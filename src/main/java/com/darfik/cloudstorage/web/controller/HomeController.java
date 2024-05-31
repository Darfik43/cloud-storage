package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.file.FileDto;
import com.darfik.cloudstorage.domain.s3storage.file.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Home controller", description = "Retrieving user's storage from " +
        "Storage")
@RequiredArgsConstructor
public class HomeController {

    private final S3FileService s3FileService;

    @GetMapping("/")
    @Operation
    public String showHomePage(@RequestParam(required = false) String path,
                               @AuthenticationPrincipal User owner,
                               Model model) {
        String currentPath = path != null ? path : "";
        List<FileDto> folderContent =
                s3FileService.getUserFiles(owner.getUsername(), currentPath,
                        false);

        model.addAttribute("folderContent", folderContent);
        model.addAttribute("currentPath", currentPath);
        return "home";
    }

}
