package com.github.sirmarjan.memy.model.entities;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false)
    private String commentText;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime create = null;

    @ManyToOne(fetch = FetchType.LAZY)
    private Meme meme = null;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user = null;

    @Column(nullable = false)
    private Boolean deleted = false;
}
