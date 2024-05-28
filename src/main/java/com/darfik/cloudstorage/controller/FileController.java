package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.dto.FileDeleteRequest;
import com.darfik.cloudstorage.dto.FileRenameRequest;
import com.darfik.cloudstorage.dto.FileUploadRequest;
import com.darfik.cloudstorage.service.AppFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @Operation(summary = "Upload a file")
    public RedirectView uploadFile(@Valid @ModelAttribute("fileUploadRequest") FileUploadRequest fileUploadRequest) {
        appFileService.uploadFile(fileUploadRequest);

        return new RedirectView("/");
    }

    @PostMapping("/rename")
    @Operation(summary = "Rename a file")
    public RedirectView renameFile(@Valid @ModelAttribute("fileRenameRequest") FileRenameRequest fileRenameRequest) {
        appFileService.renameFile(fileRenameRequest);

        return new RedirectView("/");
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete a file")
    public RedirectView deleteFile(@Valid @ModelAttribute("fileDeleteRequest") FileDeleteRequest fileDeleteRequest) {
        appFileService.deleteFile(fileDeleteRequest);

        return new RedirectView("/");
    }

}