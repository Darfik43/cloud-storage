package com.darfik.cloudstorage.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserRequest {
    @NotBlank (message = "username should not be null")
    @NotNull
    private String email;
    @NotBlank (message = "password should not be null")
    @NotNull
    private String password;
    private String matchingPassword;
}

