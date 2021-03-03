package com.github.sirmarjan.memy.controller;


import com.github.sirmarjan.memy.service.RegistrationService;
import com.github.sirmarjan.memy.transportobject.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/registration")
public class RegistrationPageController {

    private final RegistrationService registrationService;


    @Autowired
    public RegistrationPageController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showRegistration() {
        return "s_registration";
    }

    @PostMapping
    public String processRegistration(@Validated @ModelAttribute final RegistrationForm registrationForm,
                                      final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasFieldErrors()) {

            return "s_registration";
        } else {
            final var registrationResults = registrationService.registerUser(
                    registrationForm.getUsername(),
                    registrationForm.getPassword(),
                    registrationForm.getEmail());
            if (registrationResults.contains(RegistrationService.RegistrationResult.OK)) {
                return "redirect:/login?registration";
            } else {
                model.addAttribute("registrationErrors", registrationResults
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toList()));
                return "s_registration";
            }
        }
    }

    @ModelAttribute
    private void addAttributes(final Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("registrationErrors", Collections.emptyList());
    }
}
