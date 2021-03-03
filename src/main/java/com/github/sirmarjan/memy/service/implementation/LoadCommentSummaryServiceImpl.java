package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.model.CommentsRepository;
import com.github.sirmarjan.memy.service.LoadCommentSummaryService;
import com.github.sirmarjan.memy.transportobject.CommentSummary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Getter(AccessLevel.PACKAGE)
public class LoadCommentSummaryServiceImpl implements LoadCommentSummaryService {

    @NonNull
    private final CommentsRepository commentsRe;

    @Autowired
    public LoadCommentSummaryServiceImpl(@NonNull final CommentsRepository commentsRe) {
        this.commentsRe = commentsRe;
    }

    @Override
    public @NonNull List<CommentSummary> getMemeCommentSummaryList(final long memeId) {
        final var comments = getCommentsRe().findCommentsByMemeIdOrderByCreateDesc(memeId);
        return comments.stream().map(comment -> CommentSummary.builder()
                .commentId(comment.getId())
                .commentText(comment.getCommentText())
                .username(comment.getUser().getUsername())
                .createTimestamp(comment.getCreate().toEpochSecond(ZoneOffset.UTC))
                .build()).collect(Collectors.toList());
    }

}
