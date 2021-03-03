package com.github.sirmarjan.memy.model;

import com.github.sirmarjan.memy.model.entities.Comment;
import com.github.sirmarjan.memy.model.entities.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

    @Query("select comment from Comment comment where comment.meme.id = :memeId order by comment.create desc")
    List<Comment> findCommentsByMemeIdOrderByCreateDesc(long memeId);

}
