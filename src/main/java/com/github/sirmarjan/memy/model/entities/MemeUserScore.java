package com.github.sirmarjan.memy.model.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
@NoArgsConstructor
@Entity
public class MemeUserScore {
    @EmbeddedId
    private MemUserScoreId id = new MemeUserScore.MemUserScoreId();

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("memeId")
    private Meme meme;

    @Column(nullable = false)
    private boolean positiveScore = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    private User user = null;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Embeddable
    public static class MemUserScoreId implements Serializable {
        private Long memeId = null;

        private Long userId = null;

    }
}

