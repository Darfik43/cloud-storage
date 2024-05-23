package com.darfik.cloudstorage.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    @ResponseBody
    public List<AppUser> getUsers() {
        return appUserService.getUsers();
    }

    @GetMapping("/sign-up")
    public String showRegistrationForm() {
        return "sign-up";
    }


    @PostMapping("/sign-up")
    public String registerNewUser(@ModelAttribute @Valid AppUserRequest appUserRequest) throws UserAlreadyExistsException {
            appUserService.addNewUser(appUserRequest);
            return "redirect:/users";
    }
}
