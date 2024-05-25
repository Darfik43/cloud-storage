package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.RegistrationDto;
import com.darfik.cloudstorage.exception.UserAlreadyExistsException;
import com.darfik.cloudstorage.model.AppUser;
import com.darfik.cloudstorage.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
              "USER");

            appUserRepository.save(appUser);
        } else {
            throw new UserAlreadyExistsException("User already exists" + registrationDto.getEmail());
        }
    }

    private boolean userExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }
}
