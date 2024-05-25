package com.darfik.cloudstorage.user;

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
    public String registerNewUser(@Valid AppUserRequest appUserRequest) throws UserAlreadyExistsException {
        userService.registerNewUser(appUserRequest);
        return "redirect:/login";
    }
}
