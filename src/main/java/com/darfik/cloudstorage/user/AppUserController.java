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

    @GetMapping("/signup")
    public String showRegistrationForm() {
        return "signup";
    }


    //TODO Password validation DONE
    //TODO Email validation DONE
    //TODO Password confirmation DONE
    // Check up @ModelAttribute("appUserRequest") DONE
    @PostMapping("/signup")
    public String registerNewUser(@Valid AppUserRequest appUserRequest) throws UserAlreadyExistsException {
            appUserService.addNewUser(appUserRequest);
            return "redirect:/users";
    }
}
