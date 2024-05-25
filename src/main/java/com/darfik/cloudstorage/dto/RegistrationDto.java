package com.darfik.cloudstorage.dto;

import com.darfik.cloudstorage.validation.PasswordMatches;
import com.darfik.cloudstorage.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PasswordMatches
public class RegistrationDto {

    @NotBlank (message = "email should not be null")
    @NotNull
    @Email(message = "Invalid email", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank (message = "password should not be null")
    @NotNull
    @ValidPassword
    private String password;

    @NotBlank (message = "Confirm should not be null")
    @NotNull (message = "Confirm should not be null")
    private String matchingPassword;
}

