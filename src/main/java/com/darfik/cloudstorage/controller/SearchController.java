package com.darfik.cloudstorage.controller;

import com.darfik.cloudstorage.dto.AppFileDto;
import com.darfik.cloudstorage.dto.SearchRequest;
import com.darfik.cloudstorage.service.SearchService;
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
    public String searchFiles(@AuthenticationPrincipal User user, SearchRequest searchRequest, Model model) {
        searchRequest.setOwner(user.getUsername());
        List<AppFileDto> searchResults = searchService.searchFiles(searchRequest);
        model.addAttribute("searchResults", searchResults);
        return "home";
    }

}
