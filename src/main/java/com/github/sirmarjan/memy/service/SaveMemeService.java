package com.github.sirmarjan.memy.service;

import com.github.sirmarjan.memy.service.exception.UserNotFoundException;
import lombok.NonNull;

import javax.transaction.Transactional;


public interface SaveMemeService {
    /**
     * Save new meme from provided data.
     *
     * Save new meme and assign it to required associations in other entities;
     *
     * @param title meme title
     * @param image valid image data
     * @param userId userId, if null meme is stead as it have anonymous author
     * @throws UserNotFoundException if user identified by {@param userId} cannot be bound
     */
    void saveMeme(@NonNull String title, @NonNull byte[] image, Long userId) throws UserNotFoundException;
}
