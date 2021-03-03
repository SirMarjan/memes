package com.github.sirmarjan.memy.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class Meme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author = null;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createTime = null;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "meme", orphanRemoval = true)
    private ImageLob imageLob = null;

    @Column(nullable = false)
    private int negativeScoreSum = 0;

    @Column(nullable = false)
    private int positiveScoreSum = 0;

    @Column(nullable = false)
    private String title = null;

    public void addOrReplaceImageLob(@NonNull final ImageLob imageLob){
        final var currentImageLob = getImageLob();
        if (currentImageLob!=null){
            currentImageLob.setMeme(null);
        }
        if(imageLob != null){
            imageLob.setMeme(this);
        }
        setImageLob(imageLob);

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Meme)) return false;
        final Meme meme = (Meme) o;
        return Objects.equals(getId(), meme.getId()) && Objects.equals(getTitle(), meme.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }


}
