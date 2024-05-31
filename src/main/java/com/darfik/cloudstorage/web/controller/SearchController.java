package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.s3storage.file.FileResponse;
import com.darfik.cloudstorage.domain.s3storage.file.SearchRequest;
import com.darfik.cloudstorage.domain.s3storage.file.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public String searchFiles(@AuthenticationPrincipal User owner, SearchRequest searchRequest, Model model) {
        List<FileResponse> searchResults = searchService.searchFiles(searchRequest, owner.getUsername());
        model.addAttribute("searchResults", searchResults);
        return "home";
    }

}
