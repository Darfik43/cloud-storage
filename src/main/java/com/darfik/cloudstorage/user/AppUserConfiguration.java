package com.darfik.cloudstorage.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppUserConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository appUserRepository) {
        return args -> {
            AppUser testUser1 = new AppUser("test1", "pswrd");
            AppUser testUser2 = new AppUser("test2", "pswrd");

            appUserRepository.saveAll(List.of(testUser1, testUser2));
        };
    }
}
