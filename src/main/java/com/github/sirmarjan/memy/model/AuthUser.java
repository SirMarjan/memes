package com.github.sirmarjan.memy.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Value
@Builder
public class AuthUser implements UserDetails, Serializable {

    @NonNull
    long id;

    @NonNull
    Collection<? extends GrantedAuthority> authorities;

    @NonNull
    boolean enabled;

    @NonNull
    String password;

    @NonNull
    String username;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
