package com.github.sirmarjan.memy.controller;

import com.github.sirmarjan.memy.model.ImagesLobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImagesLobRepository imagesLobRepository;

    @Autowired
    public ImageController(final ImagesLobRepository imagesLobRepository) {
        this.imagesLobRepository = imagesLobRepository;
    }

    @GetMapping(value = "/{image_id}", produces = "image/*")
    public ResponseEntity<byte[]> loadImage(@PathVariable final long image_id) {
        final var imageLob = imagesLobRepository.findById(image_id);

        return imageLob.map(lob -> ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(lob.getImage()))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }


}
