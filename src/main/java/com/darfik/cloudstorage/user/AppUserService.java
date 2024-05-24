package com.darfik.cloudstorage.user;

import com.darfik.cloudstorage.mapper.AppUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AppUserMapper mapper;

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    public void addNewUser(AppUserRequest appUserRequest) {
        if (!userExists(appUserRequest.getEmail())) {
            appUserRepository.save(mapper.dtoToModel(appUserRequest));
        } else {
            throw new UserAlreadyExistsException("User already exists" + appUserRequest.getEmail());
        }
    }

    private boolean userExists(String email) {
        return appUserRepository.findAppUserByEmail(email).isPresent();
    }
}
