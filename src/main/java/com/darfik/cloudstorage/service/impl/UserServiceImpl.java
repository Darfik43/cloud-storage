package com.darfik.cloudstorage.service.impl;

import com.darfik.cloudstorage.dto.RegistrationDto;
import com.darfik.cloudstorage.exception.ResourceNotFoundException;
import com.darfik.cloudstorage.exception.UserAlreadyExistsException;
import com.darfik.cloudstorage.model.AppUser;
import com.darfik.cloudstorage.model.Role;
import com.darfik.cloudstorage.repository.AppUserRepository;
import com.darfik.cloudstorage.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerNewUser(RegistrationDto registrationDto) {
        if (!userExists(registrationDto.getEmail())) {
            AppUser appUser = new AppUser(
              registrationDto.getEmail(),
              passwordEncoder.encode(registrationDto.getPassword()),
                    Set.of(Role.ROLE_USER));

            appUserRepository.save(appUser);
        } else {
            throw new UserAlreadyExistsException("User already exists" + registrationDto.getEmail());
        }
    }

    public AppUser getByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private boolean userExists(String email) {
        return appUserRepository.existsByEmail(email);
    }

}
