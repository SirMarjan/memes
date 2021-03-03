package com.github.sirmarjan.memy.model;

import com.github.sirmarjan.memy.model.entities.Meme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemesRepository extends JpaRepository<Meme, Long> {

    Page<Meme> findAllByTitleContains(String title, Pageable pageable);

}
