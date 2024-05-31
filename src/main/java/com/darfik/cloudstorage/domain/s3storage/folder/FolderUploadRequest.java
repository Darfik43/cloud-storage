package com.darfik.cloudstorage.domain.s3storage.folder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FolderUploadRequest extends FolderRequest {

    private List<MultipartFile> files;

    public FolderUploadRequest(List<MultipartFile> files, String owner) {
        super(owner);
        this.files = files;
    }

}
