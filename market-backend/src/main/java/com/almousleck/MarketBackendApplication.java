package com.almousleck;

import com.almousleck.entites.Role;
import com.almousleck.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
public class MarketBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RoleRepository roleRepository) {
        return _ -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(
                        Role.builder()
                                .name("ROLE_USER")
                                .build()
                );
            }
        };
    }
}
