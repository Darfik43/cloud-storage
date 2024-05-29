package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.dto.AppFileDto;
import com.darfik.cloudstorage.service.AppFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
@Tag(name = "Home controller", description = "Home API")
@RequiredArgsConstructor
public class HomeController {

    private final AppFileService appFileService;

    @GetMapping("/")
    public String getFiles(@RequestParam(required = false) String path, @AuthenticationPrincipal User user, Model model) {
        String currentPath = path != null ? path : "";
        List<AppFileDto> folderContent = appFileService.getUserFiles(user.getUsername(), currentPath);

        model.addAttribute("folderContent", folderContent);
        model.addAttribute("currentPath", currentPath);
        return "home";
    }

}
