package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.model.AuthUser;
import com.github.sirmarjan.memy.model.UsersRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Getter(AccessLevel.PROTECTED)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRe;

    @Autowired
    public UserDetailsServiceImpl(@NonNull final UsersRepository usersRe) {
        this.usersRe = usersRe;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull final String s) throws UsernameNotFoundException {
        return getUsersRe().findByUsername(s)
                .map(user -> AuthUser.builder().id(user.getId())
                        .authorities(user.getAuthorities())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .enabled(user.isEnabled())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(s + "not found"));
    }
}
