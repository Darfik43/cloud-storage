package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.service.AppFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/")
@Tag(name = "Home controller", description = "Home API")
@RequiredArgsConstructor
public class HomeController {

    private final AppFileService appFileService;

    @GetMapping()
    public String showHomePage(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "path", required = false,
                    defaultValue = "") String path,
            Model model) {

        model.addAttribute(appFileService.getUserFiles(user.getUsername(), path));
        return "home";
    }

}
