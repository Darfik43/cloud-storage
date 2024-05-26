package com.darfik.cloudstorage.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
@Tag(name = "Home controller", description = "Home API")
public class HomeController {

    @GetMapping()
    public String showHomePage() {
        return "home";
    }

}
