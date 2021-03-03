package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.service.ConvertImageService;
import com.github.sirmarjan.memy.service.exception.ImageConversionException;
import lombok.NonNull;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConvertImageToPNGServiceImpl implements ConvertImageService {

    @Override
    @NonNull
    public byte[] convertImage(@NonNull final byte[] image) throws ImageConversionException {
        try {
            final var bufferedImage = Imaging.getBufferedImage(image);
            return Imaging.writeImageToBytes(bufferedImage, ImageFormats.PNG, null);
        } catch (final ImageReadException | ImageWriteException | IOException imageReWrException) {
            throw new ImageConversionException(imageReWrException);
        }
    }
}
