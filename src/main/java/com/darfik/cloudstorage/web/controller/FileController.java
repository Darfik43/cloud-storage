package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.file.S3FileService;
import com.darfik.cloudstorage.domain.s3storage.file.FileDeleteRequest;
import com.darfik.cloudstorage.domain.s3storage.file.FileRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.file.FileUploadRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "File controller", description = "File interaction API, it doesn't work with folders")
public class FileController {

    private final S3FileService s3FileService;
    private final String owner = SecurityContextHolder.getContext().getAuthentication().getName();

    @PostMapping("/upload")
    @Operation
    public RedirectView uploadFile(@Valid FileUploadRequest fileUploadRequest) {
        fileUploadRequest.setOwner(owner);
        s3FileService.uploadFile(fileUploadRequest);

        return new RedirectView("/");
    }

    @PostMapping("/rename")
    @Operation
    public RedirectView renameFile(@Valid FileRenameRequest fileRenameRequest) {
        fileRenameRequest.setOwner(owner);
        s3FileService.renameFile(fileRenameRequest);

        return new RedirectView("/");
    }

    @DeleteMapping("/delete")
    @Operation
    public RedirectView deleteFile(@Valid FileDeleteRequest fileDeleteRequest) {
        fileDeleteRequest.setOwner(owner);
        s3FileService.deleteFile(fileDeleteRequest);

        return new RedirectView("/");
    }

}