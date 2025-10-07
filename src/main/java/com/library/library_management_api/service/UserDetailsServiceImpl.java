package com.library.library_management_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.library_management_api.dto.LoginDTO;
import com.library.library_management_api.dto.AuthResponseDTO;
import com.library.library_management_api.dto.SignupDTO;
import com.library.library_management_api.persistence.entity.RoleEntity;
import com.library.library_management_api.persistence.entity.UserEntity;
import com.library.library_management_api.persistence.repository.RoleRepository;
import com.library.library_management_api.persistence.repository.UserRepository;
import com.library.library_management_api.util.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with username: " + username));

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                userEntity.getRoles().forEach(
                                role -> authorities.add(
                                                new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

                userEntity.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                                .forEach(permission -> authorities
                                                .add(new SimpleGrantedAuthority(permission.getName())));

                return new User(
                                userEntity.getUsername(),
                                userEntity.getPassword(),
                                userEntity.isEnabled(),
                                userEntity.isAccountNonExpired(),
                                userEntity.isCredentialsNonExpired(),
                                userEntity.isAccountNonLocked(),
                                authorities);
        }

        public AuthResponseDTO loginUser(LoginDTO authLoginRequest) {
                String username = authLoginRequest.username();
                String password = authLoginRequest.password();

                Authentication authentication = this.authenticate(username, password);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String accessToken = jwtUtils.createToken(authentication);

                AuthResponseDTO authResponse = new AuthResponseDTO(username, "User logged successfully", accessToken, true);

                return authResponse;
        }

        private Authentication authenticate(String username, String password) {
                UserDetails userDetails = this.loadUserByUsername(username);

                if (userDetails == null) {
                        throw new BadCredentialsException("Username or password is incorrect");
                }

                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                        throw new BadCredentialsException("Username or password is incorrect");
                }

                return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                                userDetails.getAuthorities());
        }

        public AuthResponseDTO signupUser(SignupDTO authSignupRequest) {
                String username = authSignupRequest.username();
                String password = authSignupRequest.password();
                List<String> roleNames = authSignupRequest.roleRequest().roleNamesList();

                Set<RoleEntity> rolesSet = roleRepository.findRoleEntitiesByRoleEnumIn(roleNames).stream()
                                .collect(Collectors.toSet());

                if (rolesSet.isEmpty()) {
                        throw new IllegalArgumentException("The specified roles do not exist");
                }

                UserEntity userEntity = UserEntity.builder()
                                .username(username)
                                .password(passwordEncoder.encode(password))
                                .roles(rolesSet)
                                .isEnabled(true)
                                .accountNonLocked(true)
                                .accountNonExpired(true)
                                .credentialsNonExpired(true)
                                .build();

                UserEntity createdUser = userRepository.save(userEntity);

                ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

                createdUser.getRoles().forEach(
                                role -> authorities.add(
                                                new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

                createdUser.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                                .forEach(permission -> authorities
                                                .add(new SimpleGrantedAuthority(permission.getName())));

                Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getUsername(),
                                createdUser.getPassword(), authorities);
                String accessToken = jwtUtils.createToken(authentication);

                AuthResponseDTO authResponse = new AuthResponseDTO(username, "User registered successfully", accessToken,
                                true);
                return authResponse;
        }

}
