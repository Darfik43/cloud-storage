package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.exception.UserAlreadyExistsException;
import com.darfik.cloudstorage.domain.user.RegistrationDto;
import com.darfik.cloudstorage.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
@Tag(name = "User controller", description = "User API")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation
    public String showRegistrationForm() {
        return "signup";
    }

    @Operation(summary = "Register a new user")
    @PostMapping
    public String createUser(@Valid RegistrationDto registrationDto) throws UserAlreadyExistsException {
        userService.registerNewUser(registrationDto);
        return "redirect:/login";
    }

}
