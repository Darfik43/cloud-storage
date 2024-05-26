package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.dto.FileUploadRequest;
import com.darfik.cloudstorage.service.AppFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "File controller", description = "File controller")
public class FileController {

    private final AppFileService appFileService;

    @PostMapping("/upload")
    @Operation(summary = "Upload file")
    public RedirectView uploadFile(@ModelAttribute("fileUploadRequest") FileUploadRequest fileUploadRequest) {
        appFileService.uploadFile(fileUploadRequest);

        return new RedirectView("/");
    }

}
