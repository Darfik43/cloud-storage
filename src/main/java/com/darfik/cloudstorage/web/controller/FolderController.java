package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.folder.AppFolderService;
import com.darfik.cloudstorage.domain.s3storage.folder.FolderRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.FolderUploadRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/folders")
@Tag(name = "Folder controller", description = "Folder interaction API")
public class FolderController {

    private final AppFolderService appFolderService;

    @PostMapping("/upload")
    @Operation
    public String uploadFolder(
            @AuthenticationPrincipal User user,
            @Valid FolderUploadRequest folderUploadRequest) {
        folderUploadRequest.setOwner(user.getUsername());
        appFolderService.uploadFolder(folderUploadRequest);

        return "";
    }

    @PostMapping("/rename")
    @Operation
    public String renameFolder(@AuthenticationPrincipal User user,
                               @Valid FolderRenameRequest folderRenameRequest) {
        folderRenameRequest.setOwner(user.getUsername());
        appFolderService.renameFolder(folderRenameRequest);

        return "";
    }

}
