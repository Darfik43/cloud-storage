package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.folder.FolderRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.FolderUploadRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.S3FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/folders")
@Tag(name = "Folder controller", description = "Folder interaction API")
public class FolderController {

    private final String owner =
            SecurityContextHolder.getContext().getAuthentication().getName();
    private final S3FolderService s3FolderService;

    @PostMapping("/upload")
    @Operation
    public String uploadFolder(
            @Valid FolderUploadRequest folderUploadRequest) {
        folderUploadRequest.setOwner(owner);
        s3FolderService.uploadFolder(folderUploadRequest);

        return "";
    }

    @PostMapping("/rename")
    @Operation
    public String renameFolder(@Valid FolderRenameRequest folderRenameRequest) {
        folderRenameRequest.setOwner(owner);
        s3FolderService.renameFolder(folderRenameRequest);

        return "";
    }

}
