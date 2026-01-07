package com.brenobaise.hometeq_spring.config;

import com.brenobaise.hometeq_spring.entities.Role;
import com.brenobaise.hometeq_spring.entities.RoleName;
import com.brenobaise.hometeq_spring.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserRoleInitConfig {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner iniRoles(){
        return args -> {
            ensure(RoleName.ROLE_USER);
            ensure(RoleName.ROLE_ADMIN);
        };
    }

    /**
     * fetches a role by name or creates a new role.
     * @param name
     */
    private void ensure(RoleName name) {
        roleRepository.findByName(name).orElseGet(() ->
                roleRepository.save(Role.builder().name(name).build())
        );
    }
}
