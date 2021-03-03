package com.github.sirmarjan.memy.controller;

import com.github.sirmarjan.memy.model.AuthUser;
import com.github.sirmarjan.memy.service.LoadCommentSummaryService;
import com.github.sirmarjan.memy.service.LoadMemeSummaryService;
import com.github.sirmarjan.memy.service.ManageCommentService;
import com.github.sirmarjan.memy.service.ManageMemeUserScoreService;
import com.github.sirmarjan.memy.service.exception.EntityNotFoundException;
import com.github.sirmarjan.memy.service.exception.MemeNotFoundException;
import com.github.sirmarjan.memy.transportobject.CommentPostDTO;
import com.github.sirmarjan.memy.transportobject.CommentSummary;
import com.github.sirmarjan.memy.transportobject.MemeSummary;
import com.github.sirmarjan.memy.transportobject.UserScorePostDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
@Controller
@RequestMapping("/meme")
public class MemePageController {

    @NonNull
    private final LoadCommentSummaryService loadCommentSummaryService;

    @NonNull
    private final LoadMemeSummaryService loadMemeSummaryService;

    @NonNull
    private final ManageCommentService manageCommentService;

    @NonNull
    private final ManageMemeUserScoreService manageMemeUserScoreService;

    @Autowired
    public MemePageController(
            @NonNull final LoadMemeSummaryService loadMemeSummaryService,
            @NonNull final ManageMemeUserScoreService manageMemeUserScoreService,
            @NonNull final LoadCommentSummaryService loadCommentSummaryService,
            @NonNull final ManageCommentService manageCommentService) {
        this.loadMemeSummaryService = loadMemeSummaryService;
        this.manageMemeUserScoreService = manageMemeUserScoreService;
        this.loadCommentSummaryService = loadCommentSummaryService;
        this.manageCommentService = manageCommentService;
    }

    @GetMapping(value = "/{memeId}/comment")
    @ResponseBody
    public ResponseEntity<List<CommentSummary>> loadComments(
            @PathVariable final long memeId) {
        return ResponseEntity.ok(getLoadCommentSummaryService().getMemeCommentSummaryList(memeId));
    }

    @GetMapping(value = "/{memeId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<MemeSummary> loadMeme(
            @PathVariable final long memeId,
            @AuthenticationPrincipal final AuthUser authUser) throws EntityNotFoundException {
        return ResponseEntity.ok(loadMemeSummary(memeId, authUser));
    }

    @PostMapping(value = "/{memeId}/comment")
    @ResponseBody
    public ResponseEntity<CommentSummary> postComment(
            @PathVariable final long memeId,
            @RequestBody final CommentPostDTO commentPostDTO,
            @AuthenticationPrincipal final AuthUser authUser
    ) throws MemeNotFoundException {
        if (authUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            final var newCommentSummary = getManageCommentService().saveComment(authUser.getId(), memeId, commentPostDTO.getCommentText());
            return ResponseEntity.status(HttpStatus.CREATED).body(newCommentSummary);
        }
    }

    @PostMapping(value = "/{memeId}/score", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Void> postUserScore(
            @PathVariable final long memeId,
            @RequestBody final UserScorePostDTO userScorePostDTO,
            @AuthenticationPrincipal final AuthUser authUser) throws MemeNotFoundException {
        if (authUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            getManageMemeUserScoreService().modifyMemeScore(authUser.getId(), memeId, userScorePostDTO.getScoreState());
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{memeId}")
    public String showMeme(
            @PathVariable final long memeId,
            final Model model,
            @AuthenticationPrincipal final AuthUser authUser) throws EntityNotFoundException {
        model.addAttribute("memeSummary",
                loadMemeSummary(memeId, authUser));
        return "s_meme";
    }

    @NonNull
    private MemeSummary loadMemeSummary(
            final long memeId,
            final AuthUser authUser) throws EntityNotFoundException {
        return getLoadMemeSummaryService().getMemeSummary(memeId, authUser != null ? authUser.getId() : null);
    }


}
