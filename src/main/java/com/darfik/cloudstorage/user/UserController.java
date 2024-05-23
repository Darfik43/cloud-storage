package com.darfik.cloudstorage.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //TODO It is to be @Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final AppUserService appUserService;
    @GetMapping
    public List<AppUser> getUsers() {
        return appUserService.getUsers();
    }
}
