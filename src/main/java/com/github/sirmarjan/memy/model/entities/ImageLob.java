package com.github.sirmarjan.memy.model.entities;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class ImageLob {

    @EqualsAndHashCode.Include
    @Id
    private Long id = null;

    @Lob
    @Column(nullable = false)
    private byte[] image = null;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Meme meme = null;

}
