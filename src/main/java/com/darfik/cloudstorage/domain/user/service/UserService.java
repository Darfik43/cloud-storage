package com.darfik.cloudstorage.domain.user.service;

import com.darfik.cloudstorage.domain.user.dto.RegistrationRequest;

public interface UserService {

    void registerNewUser(RegistrationRequest registrationRequest);

    Long getUserIdByEmail(String email);

}
