package com.darfik.cloudstorage.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }
}
