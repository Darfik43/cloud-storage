package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.AppUserRequest;

public interface UserService {
    void registerNewUser(AppUserRequest appUserRequest);
}
