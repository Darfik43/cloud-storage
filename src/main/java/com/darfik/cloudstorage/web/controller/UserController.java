package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.exception.UserAlreadyExistsException;
import com.darfik.cloudstorage.domain.user.RegistrationRequest;
import com.darfik.cloudstorage.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView createUser(@Valid RegistrationRequest registrationRequest) throws UserAlreadyExistsException {
        userService.registerNewUser(registrationRequest);
        return new RedirectView("/login");
    }

}
