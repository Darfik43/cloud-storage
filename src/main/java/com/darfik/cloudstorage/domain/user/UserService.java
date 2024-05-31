package com.darfik.cloudstorage.domain.user;

public interface UserService {

    void registerNewUser(RegistrationRequest registrationRequest);

    Long getUserIdByEmail(String email);

}
