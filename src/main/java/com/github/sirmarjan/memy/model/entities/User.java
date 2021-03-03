package com.github.sirmarjan.memy.model.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createTime = null;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "author")
    private Set<Meme> createdMemes = new LinkedHashSet<>();

    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true)
    private String email = null;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private String password = null;

    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true)
    private String username = null;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("BASE_ROLE"));
    }

    public void addCreatedMeme(@NonNull final Meme meme){
        getCreatedMemes().add(meme);
        meme.setAuthor(this);
    }

    public void removeCreatedMeme(@NonNull final Meme meme){
        getCreatedMemes().remove(meme);
        meme.setAuthor(null);
    }

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

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
