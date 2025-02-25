package com.site.hammerdown.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.hammerdown.common.model.AppRole;
import com.site.hammerdown.common.model.Role;
import com.site.hammerdown.user.model.User;
import com.site.hammerdown.common.repository.RoleRepository;
import com.site.hammerdown.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {


    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {

            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newSellerRole);
                    });

            Role godRole = roleRepository.findByRoleName(AppRole.ROLE_SUPERADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_SUPERADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> adminRoles = Set.of(adminRole);
            Set<Role> godRoles = Set.of(godRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
                User user1 = User.builder()
                        .userName("user1")
                        .password(passwordEncoder.encode("password"))
                        .email("user1@example.com")
                        .build();
               userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("admin1")) {
                User admin1 = User.builder().userName("admin1").password(passwordEncoder.encode("password")).email("admin@example.com").build();
                userRepository.save(admin1);
            }

            if (!userRepository.existsByUserName("god")) {
                User god = User.builder()
                        .userName("god").password(passwordEncoder.encode("password"))
                        .email("god@example.com")
                        .build();
                userRepository.save(god);
            }

            // Update roles for existing users
            userRepository.findByUserName("user1").ifPresent(user -> {
                user.setRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUserName("admin1").ifPresent(seller -> {
                seller.setRoles(adminRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("god").ifPresent(admin -> {
                admin.setRoles(godRoles);
                userRepository.save(admin);
            });
        };
    }
}
