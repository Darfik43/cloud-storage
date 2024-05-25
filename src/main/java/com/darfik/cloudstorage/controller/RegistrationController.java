package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.dto.RegistrationDto;
import com.darfik.cloudstorage.exception.UserAlreadyExistsException;
import com.darfik.cloudstorage.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;


    @GetMapping
    public String showRegistrationForm() {
        return "signup";
    }

    @PostMapping
    public String registerNewUser(@Valid RegistrationDto registrationDto) throws UserAlreadyExistsException {
        userService.registerNewUser(registrationDto);
        return "redirect:/login";
    }
}