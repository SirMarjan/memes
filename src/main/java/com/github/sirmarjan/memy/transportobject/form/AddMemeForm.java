package com.github.sirmarjan.memy.transportobject.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddMemeForm {

    @NotNull
    private MultipartFile image;

    @NotBlank
    @Size(max = 64)
    private String title;

}
