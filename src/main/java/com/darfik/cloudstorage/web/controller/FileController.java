package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.file.AppFileService;
import com.darfik.cloudstorage.domain.s3storage.file.FileDeleteRequest;
import com.darfik.cloudstorage.domain.s3storage.file.FileRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.file.FileUploadRequest;
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
@Tag(name = "File controller", description = "File interaction API, it doesn't work with folders")
public class FileController {

    private final AppFileService appFileService;

    @PostMapping("/upload")
    @Operation
    public RedirectView uploadFile(@AuthenticationPrincipal User user,
                                   @Valid FileUploadRequest fileUploadRequest) {
        fileUploadRequest.setOwner(user.getUsername());
        appFileService.uploadFile(fileUploadRequest);

        return new RedirectView("/");
    }

    @PostMapping("/rename")
    @Operation
    public RedirectView renameFile(@AuthenticationPrincipal User user,
                                   @Valid FileRenameRequest fileRenameRequest) {
        fileRenameRequest.setOwner(user.getUsername());
        appFileService.renameFile(fileRenameRequest);

        return new RedirectView("/");
    }

    @DeleteMapping("/delete")
    @Operation
    public RedirectView deleteFile(@AuthenticationPrincipal User user,
                                   @Valid FileDeleteRequest fileDeleteRequest) {
        fileDeleteRequest.setOwner(user.getUsername());
        appFileService.deleteFile(fileDeleteRequest);

        return new RedirectView("/");
    }

}