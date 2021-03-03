package com.github.sirmarjan.memy.service;

import com.github.sirmarjan.memy.service.exception.EntityNotFoundException;
import com.github.sirmarjan.memy.transportobject.MemeSummary;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface LoadMemeSummaryService {

    /**
     * Load information about meme
     *
     * Provide access to information about meme from specific user perspective.
     *
     * @param memeId meme identifier
     * @param userId user identifier, if null tread as anonymous user
     * @return user view of meme
     * @throws EntityNotFoundException if meme or user dont exist
     */
    @NonNull
    MemeSummary getMemeSummary(long memeId, Long userId) throws EntityNotFoundException;

    /**
     * Load information about all memes
     *
     * Provide access to information about all memes from specific user perspective.
     *
     * @param userId  user identifier, if null tread as anonymous user
     * @param page page number, start from 0
     * @param size page size
     * @return page of meme summaries
     */
    @NonNull Page<MemeSummary> getMemeSummaryPage(Long userId, int page, int size);


    /**
     * Load information about all memes filter by title
     *
     * Provide access to information about memes filter by title of memes contains {@param titlePart}
     *
     * @param titlePart part of title for search
     * @param userId user identifier, if null tread as anonymous user
     * @param page page number, start from 0
     * @param size page size
     * @return page of meme summaries filter by title
     */
    @NonNull Page<MemeSummary> getMemeSummaryPageByContainsInTitle(@NonNull String titlePart,
                                                                   Long userId, int page,
                                                                   int size);
}
