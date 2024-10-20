package com.library.library_management_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.library.library_management_api.config.filter.JwtTokenValidator;
import com.library.library_management_api.service.UserDetailsServiceImpl;
import com.library.library_management_api.util.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // config public endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();

                    // config private endpoints
                    http.requestMatchers("/api/genres/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("USER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/books").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.PATCH, "/api/books/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/loans/**").hasAnyRole("USER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/loans").hasAnyRole("USER");
                    http.requestMatchers(HttpMethod.PUT, "/api/loans/**").hasAnyRole("USER");

                    // config all other endpoints
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}