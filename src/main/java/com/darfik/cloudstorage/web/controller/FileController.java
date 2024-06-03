package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.file.FileDeleteRequest;
import com.darfik.cloudstorage.domain.s3storage.file.FileRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.file.FileUploadRequest;
import com.darfik.cloudstorage.domain.s3storage.file.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "File controller", description = "File interaction API, it " +
        "doesn't work with folders")
public class FileController {

    private final S3FileService s3FileService;

    @PostMapping("/upload")
    @Operation
    public RedirectView uploadFile(@AuthenticationPrincipal User owner, @Valid FileUploadRequest fileUploadRequest) {
        s3FileService.uploadFile(fileUploadRequest, owner.getUsername());

        return new RedirectView("/");
    }

    @PutMapping
    @Operation
    public RedirectView renameFile(@AuthenticationPrincipal User owner, @Valid FileRenameRequest fileRenameRequest) {
        s3FileService.renameFile(fileRenameRequest, owner.getUsername());

        return new RedirectView("/");
    }

    @DeleteMapping
    @Operation
    public RedirectView deleteFile(@AuthenticationPrincipal User owner, @Valid FileDeleteRequest fileDeleteRequest) {
        s3FileService.deleteFile(fileDeleteRequest, owner.getUsername());

        return new RedirectView("/");
    }

}