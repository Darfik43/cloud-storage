package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.dto.RegistrationDto;
import com.darfik.cloudstorage.exception.UserAlreadyExistsException;
import com.darfik.cloudstorage.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@AllArgsConstructor
@Tag(name = "Registration controller", description = "Registration API")
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    @Operation
    public String showRegistrationForm() {
        return "signup";
    }

    @Operation(summary = "Register a new user")
    @PostMapping
    public String registerNewUser(@Valid RegistrationDto registrationDto) throws UserAlreadyExistsException {
        userService.registerNewUser(registrationDto);
        return "redirect:/login";
    }

}
