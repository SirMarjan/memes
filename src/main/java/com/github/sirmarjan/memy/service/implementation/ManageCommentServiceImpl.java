package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.model.CommentsRepository;
import com.github.sirmarjan.memy.model.MemesRepository;
import com.github.sirmarjan.memy.model.UsersRepository;
import com.github.sirmarjan.memy.model.entities.Comment;
import com.github.sirmarjan.memy.model.entities.Meme;
import com.github.sirmarjan.memy.service.ManageCommentService;
import com.github.sirmarjan.memy.transportobject.CommentSummary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneOffset;

@Service
@Getter(AccessLevel.PROTECTED)
@Transactional
public class ManageCommentServiceImpl implements ManageCommentService {

    @NonNull
    final CommentsRepository commentsRepository;
    @NonNull
    final UsersRepository usersRepository;
    @NonNull
    final MemesRepository memesRepository;

    @Autowired
    public ManageCommentServiceImpl(@NonNull final CommentsRepository commentsRepository,
                                    @NonNull final UsersRepository usersRepository,
                                    @NonNull final MemesRepository memesRepository) {
        this.commentsRepository = commentsRepository;
        this.usersRepository = usersRepository;
        this.memesRepository = memesRepository;
    }

    @Override
    @NonNull
    public CommentSummary saveComment(final long userId, final long memeId, final String commentText) {
        final var userProxy = getUsersRepository().getOne(userId);
        final var memeProxy = getMemesRepository().getOne(memeId);
        final var newComment = new Comment();
        newComment.setCommentText(commentText);
        newComment.setMeme(memeProxy);
        newComment.setUser(userProxy);
        final var commentEntity = getCommentsRepository().save(newComment);
        return CommentSummary.builder()
                .commentId(commentEntity.getId())
                .commentText(commentEntity.getCommentText())
                .username(commentEntity.getUser().getUsername())
                .createTimestamp(commentEntity.getCreate().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

}
