package com.darfik.cloudstorage.domain.s3storage.file;

import lombok.Getter;

@Getter
public class SearchRequest extends FileRequest {

    private final String searchTerm;

    public SearchRequest(String searchTerm) {
        super();
        this.searchTerm = searchTerm;
    }

}
