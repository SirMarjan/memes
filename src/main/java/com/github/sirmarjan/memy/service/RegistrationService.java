package com.github.sirmarjan.memy.service;

import lombok.NonNull;

import java.util.Set;

public interface RegistrationService {

    /**
     * Check if username and email already exist in db
     *
     * @param username new username
     * @param email new email
     * @return representation of checks
     */
    @NonNull Set<RegistrationResult> checkUsernameEmailExist(@NonNull final String username,
                                                             @NonNull final String email);

    /**
     * Register new user in db
     *
     * @param username new user username
     * @param password new user password
     * @param email new user email
     * @return represents checks of uniqueness username and email
     */
    @NonNull
    Set<RegistrationResult> registerUser(@NonNull String username, @NonNull String password, @NonNull String email);

    enum RegistrationResult {
        OK,
        UsernameExist,
        EmailExist,
    }
}
