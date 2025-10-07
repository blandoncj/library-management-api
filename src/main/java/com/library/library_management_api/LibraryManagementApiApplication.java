package com.library.library_management_api;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.library.library_management_api.persistence.entity.PermissionEntity;
import com.library.library_management_api.persistence.entity.RoleEntity;
import com.library.library_management_api.persistence.entity.RoleEnum;
import com.library.library_management_api.persistence.entity.UserEntity;
import com.library.library_management_api.persistence.repository.UserRepository;

@SpringBootApplication
public class LibraryManagementApiApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApiApplication.class, args);
	}

	// @Bean
	// CommandLineRunner init(UserRepository userRepository) {
	// 	return args -> {

	// 		// create permissions
	// 		PermissionEntity createPermission = PermissionEntity.builder()
	// 				.name("CREATE")
	// 				.build();

	// 		PermissionEntity readPermission = PermissionEntity.builder()
	// 				.name("READ")
	// 				.build();

	// 		PermissionEntity updatePermission = PermissionEntity.builder()
	// 				.name("UPDATE")
	// 				.build();

	// 		PermissionEntity deletePermission = PermissionEntity.builder()
	// 				.name("DELETE")
	// 				.build();

	// 		// create roles
	// 		RoleEntity roleAdmin = RoleEntity.builder()
	// 				.roleEnum(RoleEnum.ADMIN)
	// 				.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
	// 				.build();

	// 		RoleEntity roleUser = RoleEntity.builder()
	// 		.roleEnum(RoleEnum.USER)
	// 		.permissions(Set.of(createPermission, readPermission))
	// 		.build();

	// 		// create users
	// 		UserEntity userAdmin = UserEntity.builder()
	// 				.username("root")
	// 				.password(passwordEncoder.encode("root"))
	// 				.isEnabled(true)
	// 				.accountNonExpired(true)
	// 				.accountNonLocked(true)
	// 				.credentialsNonExpired(true)
	// 				.roles(Set.of(roleAdmin))
	// 				.build();

	// 		UserEntity userUser = UserEntity.builder()
	// 				.username("user")
	// 				.password(passwordEncoder.encode("user"))
	// 				.isEnabled(true)
	// 				.accountNonExpired(true)
	// 				.accountNonLocked(true)
	// 				.credentialsNonExpired(true)
	// 				.roles(Set.of(roleUser))
	// 				.build();

	// 		userRepository.saveAll(List.of(userAdmin, userUser));
	// 	};
	// }

}
