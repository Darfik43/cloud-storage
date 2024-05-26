package com.darfik.cloudstorage.service;


import com.darfik.cloudstorage.dto.RegistrationDto;
import com.darfik.cloudstorage.exception.UserAlreadyExistsException;
import com.darfik.cloudstorage.model.AppUser;
import com.darfik.cloudstorage.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

@SpringBootTest
@Testcontainers
public class UserServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private UserService userService;

    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeEach
    void beforeEach() {
        appUserRepository.deleteAll();
    }

    @Test
    void register_shouldSaveUserInDatabase() {
        var registrationDto = new RegistrationDto();
        registrationDto.setEmail("user@gmail.com");
        registrationDto.setPassword("password");

        userService.registerNewUser(registrationDto);

        Optional<AppUser> user = appUserRepository.findByEmail("user@gmail.com");
        assertTrue(user.isPresent(), "User exists in database");
        user.ifPresent(u -> {
            assertEquals("Emails don't match","user@gmail.com", u.getEmail());
            assertNotEquals("Password should be hashed", u.getPassword(), registrationDto.getPassword());
        });
    }

    @Test
    void register_sameUserTwice_shouldThrowException() {
        var registrationDto = new RegistrationDto();
        registrationDto.setEmail("user@gmail.com");
        registrationDto.setPassword("password");

        userService.registerNewUser(registrationDto);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.registerNewUser(registrationDto)
        );
        assertEquals("User should be saved only once", appUserRepository.findAll().size(), 1);
    }

}
