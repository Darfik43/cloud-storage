package com.darfik.cloudstorage.domain.user.dto;

import com.darfik.cloudstorage.web.validation.PasswordMatches;
import com.darfik.cloudstorage.web.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



@PasswordMatches
public record RegistrationRequest(

        @NotBlank(message = "email should not be null")
        @Email(message = "Invalid email", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,

        @ValidPassword
        @NotBlank(message = "password should not be null")
        String password,

        @NotBlank(message = "Confirm should not be null")
        String matchingPassword

) {
}

