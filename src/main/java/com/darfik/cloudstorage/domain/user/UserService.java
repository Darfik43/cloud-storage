package com.darfik.cloudstorage.domain.user;

public interface UserService {

    void registerNewUser(RegistrationDto registrationDto);

    Long getUserIdByEmail(String email);

}
