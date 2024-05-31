package com.darfik.cloudstorage.domain.s3storage.folder;

public record FolderRenameRequest(String currentName, String newName) {
}
