package com.metaphorce.assessment_final.configuration;

import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Role;
import com.metaphorce.assessment_final.enums.UserStatus;
import com.metaphorce.assessment_final.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        return args -> {

            User user = User.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .phoneNumber("4775745465")
                    .status(UserStatus.ACTIVE)
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin1234"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(user);
        };
    }
}
