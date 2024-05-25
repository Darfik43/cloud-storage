package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.RegistrationDto;

public interface UserService {
    void registerNewUser(RegistrationDto registrationDto);
}
