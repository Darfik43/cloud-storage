package com.darfik.cloudstorage.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/home")
public class HomeController {

    @GetMapping
    public String showHomePage() {
        return "signup";
    }
}
