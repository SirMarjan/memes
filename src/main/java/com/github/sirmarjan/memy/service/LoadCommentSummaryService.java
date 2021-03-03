package com.github.sirmarjan.memy.service;

import com.github.sirmarjan.memy.transportobject.CommentSummary;
import lombok.NonNull;

import java.util.List;

public interface LoadCommentSummaryService {

    /**
     * Load comments as {@link CommentSummary} from database entities
     *
     * Provide access to rich in information representation of comments attached to meme.
     *
     * @param memeId meme identifier with is used to select comments
     * @return list of comments
     */
    @NonNull
    List<CommentSummary> getMemeCommentSummaryList(long memeId);

}
