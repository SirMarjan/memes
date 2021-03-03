package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.model.UsersRepository;
import com.github.sirmarjan.memy.model.entities.User;
import com.github.sirmarjan.memy.service.RegistrationService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Getter(AccessLevel.PROTECTED)
public class RegistrationServiceImpl implements RegistrationService {

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final UsersRepository usersRe;

    @Autowired
    public RegistrationServiceImpl(@NonNull final PasswordEncoder passwordEncoder,
                                   @NonNull final UsersRepository usersRe) {
        this.passwordEncoder = passwordEncoder;
        this.usersRe = usersRe;
    }

    @Override
    @NonNull
    public Set<RegistrationResult> checkUsernameEmailExist(@NonNull final String username,
                                                           @NonNull final String email) {
        final boolean freeUsername = getUsersRe().findByUsername(username).isEmpty();
        final boolean freeEmail = getUsersRe().findByEmail(email).isEmpty();

        final Set<RegistrationResult> result = new HashSet<>(2);
        if (freeUsername && freeEmail) {
            result.add(RegistrationResult.OK);
        } else {
            if (!freeUsername) {
                result.add(RegistrationResult.UsernameExist);
            }
            if (!freeEmail) {
                result.add(RegistrationResult.EmailExist);
            }
        }
        return result;
    }

    @Override
    @NonNull
    public Set<RegistrationResult> registerUser(@NonNull final String username,
                                                @NonNull final String password,
                                                @NonNull final String email) {

        final Set<RegistrationResult> result = checkUsernameEmailExist(username, email);
        if (result.contains(RegistrationResult.OK)) {
            final User user = createUser(username, password, email);
            getUsersRe().save(user);
            result.add(RegistrationResult.OK);
        }

        return Collections.unmodifiableSet(result);
    }

    @NonNull
    protected User createUser(@NonNull final String username,
                              @NonNull final String password,
                              @NonNull final String email) {
        final var newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(getPasswordEncoder().encode(password));
        newUser.setEmail(email);
        newUser.setEnabled(true);
        return newUser;
    }


}
