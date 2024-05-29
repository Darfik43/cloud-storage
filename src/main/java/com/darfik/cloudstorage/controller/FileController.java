package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.dto.FileDeleteRequest;
import com.darfik.cloudstorage.dto.FileRenameRequest;
import com.darfik.cloudstorage.dto.FileUploadRequest;
import com.darfik.cloudstorage.service.AppFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public RedirectView uploadFile(@AuthenticationPrincipal User user,
                                   @Valid FileUploadRequest fileUploadRequest) {
        fileUploadRequest.setOwner(user.getUsername());
        appFileService.uploadFile(fileUploadRequest);

        return new RedirectView("/");
    }

    @PostMapping("/rename")
    @Operation(summary = "Rename a file")
    public RedirectView renameFile(@AuthenticationPrincipal User user,
                                   @Valid FileRenameRequest fileRenameRequest) {
        fileRenameRequest.setOwner(user.getUsername());
        appFileService.renameFile(fileRenameRequest);

        return new RedirectView("/");
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete a file")
    public RedirectView deleteFile(@AuthenticationPrincipal User user,
                                   @Valid FileDeleteRequest fileDeleteRequest) {
        fileDeleteRequest.setOwner(user.getUsername());
        appFileService.deleteFile(fileDeleteRequest);

        return new RedirectView("/");
    }

}