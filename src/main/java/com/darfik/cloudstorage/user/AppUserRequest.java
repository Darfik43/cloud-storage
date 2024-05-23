package com.darfik.cloudstorage.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserRequest {
    @NotBlank (message = "username should not be null")
    private String username;
    @NotBlank (message = "password should not be null")
    private String password;
    @NotBlank (message = "Password confirmation should not be null")
    private String passwordConfirmation;
}

