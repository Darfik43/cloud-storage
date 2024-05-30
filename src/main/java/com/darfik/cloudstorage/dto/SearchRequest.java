package com.darfik.cloudstorage.dto;

import lombok.Getter;

@Getter
public class SearchRequest extends FileRequest {

    private final String searchTerm;

    public SearchRequest(String searchTerm, String owner) {
        super(owner);
        this.searchTerm = searchTerm;
    }

}
