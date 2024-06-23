package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderDeleteRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderUploadRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.service.S3FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/folders")
@Tag(name = "Folder controller", description = "Folder interaction API")
public class FolderController {

    private final S3FolderService s3FolderService;

    @PostMapping
    @Operation
    public RedirectView uploadFolder(@AuthenticationPrincipal User owner,
            @Valid FolderUploadRequest folderUploadRequest) {
        s3FolderService.uploadFolder(folderUploadRequest, owner.getUsername());

        return new RedirectView("/");
    }

    @PutMapping
    @Operation
    public RedirectView renameFolder(@AuthenticationPrincipal User owner,
                                     @Valid FolderRenameRequest folderRenameRequest) {
        s3FolderService.renameFolder(folderRenameRequest, owner.getUsername());

        return new RedirectView("/");
    }

    @DeleteMapping
    @Operation
    public RedirectView deleteFolder(@AuthenticationPrincipal User owner,
                                     @Valid FolderDeleteRequest folderDeleteRequest) {
        s3FolderService.deleteFolder(folderDeleteRequest, owner.getUsername());

        return new RedirectView("/");
    }

}
