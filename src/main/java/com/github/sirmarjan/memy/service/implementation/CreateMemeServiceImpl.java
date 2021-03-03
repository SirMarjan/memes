package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.model.MemesRepository;
import com.github.sirmarjan.memy.model.UsersRepository;
import com.github.sirmarjan.memy.model.entities.ImageLob;
import com.github.sirmarjan.memy.model.entities.Meme;
import com.github.sirmarjan.memy.model.entities.User;
import com.github.sirmarjan.memy.service.SaveMemeService;
import com.github.sirmarjan.memy.service.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Getter(AccessLevel.PROTECTED)
public class CreateMemeServiceImpl implements SaveMemeService {

    @NonNull
    private final MemesRepository memesRe;

    @NonNull
    private final UsersRepository usersRe;

    @Autowired
    public CreateMemeServiceImpl(@NonNull final MemesRepository memesRe,
                                 @NonNull final UsersRepository usersRe) {
        this.memesRe = memesRe;
        this.usersRe = usersRe;
    }


    @Override
    public void saveMeme(@NonNull final String title, @NonNull final byte[] image, final Long userId)
            throws UserNotFoundException {
        final var author = userId != null ?
                getUsersRe().findById(userId).orElseThrow(UserNotFoundException::new) : null;
        final var newMeme = constructMeme(title, image, author);
        getMemesRe().save(newMeme);
    }

    @NonNull
    protected Meme constructMeme(@NonNull final String title, @NonNull final byte[] image, final User author) {
        final var newMeme = new Meme();
        final var newImageLob = new ImageLob();
        newImageLob.setImage(image);
        newMeme.setTitle(title);
        newMeme.addOrReplaceImageLob(newImageLob);
        if (author != null) {
            author.addCreatedMeme(newMeme);
        }
        return newMeme;
    }


}
