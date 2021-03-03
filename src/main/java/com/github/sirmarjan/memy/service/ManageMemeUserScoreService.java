package com.github.sirmarjan.memy.service;

import com.github.sirmarjan.memy.model.entities.MemeUserScore;
import com.github.sirmarjan.memy.service.exception.MemeNotFoundException;
import com.github.sirmarjan.memy.transportobject.ScoreState;
import lombok.NonNull;

public interface ManageMemeUserScoreService {

    /**
     * Insert into db information about new meme user score.
     *
     * Insert new meme and update information and associations in other entities.
     *
     * @param memeId meme identifier
     * @param userId user identifier
     * @param positiveScore is new score positive
     * @throws MemeNotFoundException ff cannot find meme
     */
    void createMemeUserScore(long userId, long memeId, boolean positiveScore)
            throws MemeNotFoundException;

    /**
     * Modify user meme score by creating new object or modifying/removing existing.
     *
     * @param userId user identifier
     * @param memeId meme identifier
     * @param scoreState score type
     * @throws MemeNotFoundException if cannot find meme
     */
    void modifyMemeScore(long userId, long memeId, @NonNull ScoreState scoreState) throws MemeNotFoundException;

    /**
     * Remove existed meme score from db
     *
     * @param userId meme identifier
     * @param memeId user identifier
     */
    void removeMemeUserScore(long userId, long memeId);

    /**
     * Set meme user score as positive/negative or create if not exist.
     *
     * @param memeId meme identifier
     * @param userId user identifier
     * @param positiveScore is new score value positive
     * @throws MemeNotFoundException if cannot find meme
     */
    void setMemeUserScore(long userId, long memeId, boolean positiveScore) throws MemeNotFoundException;

    /**
     * Set existing meme user score as positive/negative .
     *
     * @param memeUserScore meme user score entity
     * @param positiveScore meme identifier
     */
    void updateMemeUserSore(@NonNull MemeUserScore memeUserScore, boolean positiveScore);
}
