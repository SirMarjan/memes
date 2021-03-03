package com.github.sirmarjan.memy.controller;

import com.github.sirmarjan.memy.model.AuthUser;
import com.github.sirmarjan.memy.service.ConvertImageService;
import com.github.sirmarjan.memy.service.SaveMemeService;
import com.github.sirmarjan.memy.service.exception.ImageConversionException;
import com.github.sirmarjan.memy.service.exception.UserNotFoundException;
import com.github.sirmarjan.memy.transportobject.form.AddMemeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/add_meme")
public class AddMemePageController {

    private final ConvertImageService convertImageService;

    private final SaveMemeService saveMemeService;

    @Autowired
    public AddMemePageController(final SaveMemeService saveMemeService,
                                 final ConvertImageService convertImageService) {
        this.saveMemeService = saveMemeService;
        this.convertImageService = convertImageService;
    }

    @PostMapping
    public String processAddMeme(
            @Validated @ModelAttribute final AddMemeForm addMemeForm,
            final BindingResult bindingResult,
            @AuthenticationPrincipal final AuthUser authUser)
            throws IOException, UserNotFoundException, ImageConversionException {
        if (bindingResult.hasFieldErrors()) {
            return "s_add_meme";
        } else {
            final var pngImage = convertImageService.convertImage(addMemeForm.getImage().getBytes());
            saveMemeService.saveMeme(addMemeForm.getTitle(),
                    pngImage,
                    authUser != null ? authUser.getId() : null);
            return "redirect:/?add";
        }
    }

    @GetMapping
    public String showAddMem() {
        return "s_add_meme";
    }

    @ModelAttribute
    private void addAttributes(final Model model) {
        model.addAttribute("addMemeForm", new AddMemeForm());
    }

}
