package com.github.sirmarjan.memy.transportobject.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 32)
    private String password;

    @NotBlank
    @Size(min = 4, max = 32)
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,32}+$")
    private String username;
}
