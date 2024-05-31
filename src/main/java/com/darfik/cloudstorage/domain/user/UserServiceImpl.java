package com.darfik.cloudstorage.domain.user;

import com.darfik.cloudstorage.domain.exception.ResourceNotFoundException;
import com.darfik.cloudstorage.domain.exception.UserAlreadyExistsException;
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
    public void registerNewUser(RegistrationRequest registrationRequest) {
        if (!userExists(registrationRequest.email())) {
            AppUser appUser = new AppUser(
                    registrationRequest.email(),
                    passwordEncoder.encode(registrationRequest.password()),
                    Set.of(Role.ROLE_USER));

            appUserRepository.save(appUser);
        } else {
            throw new UserAlreadyExistsException("User already exists" + registrationRequest.email());
        }
    }

    public Long getUserIdByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .map(AppUser::getId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with email " + email + " not found"));
    }

    private boolean userExists(String email) {
        return appUserRepository.existsByEmail(email);
    }

}
