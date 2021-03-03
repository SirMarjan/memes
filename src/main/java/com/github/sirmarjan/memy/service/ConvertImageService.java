package com.github.sirmarjan.memy.service;

import com.github.sirmarjan.memy.service.exception.ImageConversionException;
import lombok.NonNull;

import java.io.IOException;


public interface ConvertImageService {

    /**
     * Convert image preparing it to store in db.
     *
     * @param image a valid image data in bytes
     * @return bytes array containing image in new format
     * @throws ImageConversionException if any exception occur at conversion process
     */
    @NonNull
    byte[] convertImage(@NonNull byte[] image) throws ImageConversionException;

}
