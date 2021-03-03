package com.github.sirmarjan.memy.controller;

import com.github.sirmarjan.memy.model.AuthUser;
import com.github.sirmarjan.memy.service.LoadMemeSummaryService;
import com.github.sirmarjan.memy.service.implementation.LoadMemeSummaryServiceImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Getter(AccessLevel.PACKAGE)
@RequestMapping({"/", "/index"})
public class IndexPageController {

    @NonNull
    final LoadMemeSummaryService loadMemeSummaryService;

    final int pageSize = 10;

    @Autowired
    public IndexPageController(@NonNull final LoadMemeSummaryServiceImpl loadMemeSummaryService) {
        this.loadMemeSummaryService = loadMemeSummaryService;
    }

    @GetMapping
    public String getMainSite(@NonNull final Model model,
                              @RequestParam(required = false) final Integer page,
                              @RequestParam(required = false) final String search,
                              @AuthenticationPrincipal final AuthUser authUser) {
        final var memeSummaryPage = Optional.ofNullable(search).map(search_value ->
                getLoadMemeSummaryService().getMemeSummaryPageByContainsInTitle(
                        search_value,
                        authUser != null ? authUser.getId() : null,
                        page != null && page >= 0 ? page : 0,
                        getPageSize()
                )).orElseGet(() ->
                getLoadMemeSummaryService().getMemeSummaryPage(
                        authUser != null ? authUser.getId() : null,
                        page != null && page >= 0 ? page : 0,
                        getPageSize()
                ));

        model.addAttribute("memeSummaryPage", memeSummaryPage);

        return "s_index";
    }

}
