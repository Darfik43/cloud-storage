package com.darfik.cloudstorage.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Tag(name = "Login controller", description = "Login API")
public class LoginController {

    @GetMapping
    @Operation
    public String showLoginForm() {
        return "login";
    }

}
