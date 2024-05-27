package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.RegistrationDto;
import com.darfik.cloudstorage.model.AppUser;

public interface UserService {

    void registerNewUser(RegistrationDto registrationDto);

    AppUser getByEmail(String email);

}
