package com.darfik.cloudstorage.domain.s3storage.file;

public record FileDto(String owner, boolean isDir, String path, String name) {
}
