package com.darfik.cloudstorage.user;

import com.darfik.cloudstorage.mapper.AppUserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerNewUser(AppUserRequest appUserRequest) {
        if (!userExists(appUserRequest.getEmail())) {
            AppUser appUser = new AppUser(
              appUserRequest.getEmail(),
              passwordEncoder.encode(appUserRequest.getPassword()),
              "USER");

            appUserRepository.save(appUser);
        } else {
            throw new UserAlreadyExistsException("User already exists" + appUserRequest.getEmail());
        }
    }

    private boolean userExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }
}
