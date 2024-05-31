package com.darfik.cloudstorage.domain.user;

import com.darfik.cloudstorage.web.validation.PasswordMatches;
import com.darfik.cloudstorage.web.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PasswordMatches
public class RegistrationDto {

    @NotBlank(message = "email should not be null")
    @NotNull
    @Email(message = "Invalid email", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^" +
            ".-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "password should not be null")
    @NotNull
    @ValidPassword
    private String password;

    @NotBlank(message = "Confirm should not be null")
    @NotNull(message = "Confirm should not be null")
    private String matchingPassword;

}
