package com.github.sirmarjan.memy.service;

import com.github.sirmarjan.memy.transportobject.CommentSummary;
import lombok.NonNull;

public interface ManageCommentService {

    /**
     * Save new meme from provided data.
     *
     * Save new meme and assign it to required associations in other entities.
     * Return rich in information representation of created entity
     *
     * @param userId user identifier
     * @param memeId meme identifier
     * @param commentText comment content
     * @return summary of created entity
     */
    @NonNull
    CommentSummary saveComment(long userId, long memeId, String commentText);


}
