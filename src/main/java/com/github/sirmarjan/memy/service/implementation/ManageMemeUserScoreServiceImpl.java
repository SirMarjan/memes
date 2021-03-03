package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.model.MemeUserScoresRepository;
import com.github.sirmarjan.memy.model.MemesRepository;
import com.github.sirmarjan.memy.model.UsersRepository;
import com.github.sirmarjan.memy.model.entities.MemeUserScore;
import com.github.sirmarjan.memy.service.ManageMemeUserScoreService;
import com.github.sirmarjan.memy.service.exception.MemeNotFoundException;
import com.github.sirmarjan.memy.transportobject.ScoreState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Getter(AccessLevel.PROTECTED)
@Service
@Transactional
public class ManageMemeUserScoreServiceImpl implements ManageMemeUserScoreService {

    @NonNull
    private final MemeUserScoresRepository memeUserScoresRe;

    @NonNull
    private final MemesRepository memesRe;

    @NonNull
    private final UsersRepository usersRe;

    public ManageMemeUserScoreServiceImpl(
            @NonNull final MemeUserScoresRepository memeUserScoresRe,
            @NonNull final UsersRepository usersRe, @NonNull final MemesRepository memesRe) {
        this.memeUserScoresRe = memeUserScoresRe;
        this.usersRe = usersRe;
        this.memesRe = memesRe;
    }

    @Override
    public void createMemeUserScore(final long userId, final long memeId, final boolean positiveScore)
            throws MemeNotFoundException {
        final var meme = getMemesRe().findById(memeId).orElseThrow(MemeNotFoundException::new);
        final var newMemeUserScore = new MemeUserScore();
        newMemeUserScore.setMeme(meme);
        newMemeUserScore.setUser(getUsersRe().getOne(userId));
        newMemeUserScore.setPositiveScore(positiveScore);
        getMemeUserScoresRe().save(newMemeUserScore);
        if (positiveScore) {
            meme.setPositiveScoreSum(meme.getPositiveScoreSum() + 1);
        } else {
            meme.setNegativeScoreSum(meme.getNegativeScoreSum() + 1);
        }
    }

    @Override
    public void modifyMemeScore(final long userId, final long memeId, @NonNull final ScoreState scoreState)
            throws MemeNotFoundException {
        if (scoreState == ScoreState.NONE) {
            removeMemeUserScore(userId, memeId);
        } else {
            setMemeUserScore(userId, memeId, scoreState == ScoreState.POSITIVE);
        }
    }

    @Override
    public void removeMemeUserScore(final long userId, final long memeId) {
        final var musOp = getMemeUserScoresRe().findById(new MemeUserScore.MemUserScoreId(memeId, userId));
        musOp.ifPresent(mus -> {
            final var meme = mus.getMeme();
            if (mus.isPositiveScore()) {
                meme.setPositiveScoreSum(meme.getPositiveScoreSum() - 1);
            } else {
                meme.setNegativeScoreSum(meme.getNegativeScoreSum() - 1);
            }
            getMemeUserScoresRe().delete(mus);
        });

    }

    @Override
    public void setMemeUserScore(final long userId, final long memeId, final boolean positiveScore)
            throws MemeNotFoundException {
        final var musOp = getMemeUserScoresRe().findById(new MemeUserScore.MemUserScoreId(memeId, userId));
        if (musOp.isPresent()) {
            updateMemeUserSore(musOp.get(), positiveScore);
        } else {
            createMemeUserScore(userId, memeId, positiveScore);
        }
    }

    @Override
    public void updateMemeUserSore(@NonNull final MemeUserScore memeUserScore, final boolean positiveScore) {
        final var meme = memeUserScore.getMeme();
        if (memeUserScore.isPositiveScore() != positiveScore) {
            memeUserScore.setPositiveScore(positiveScore);
            if (positiveScore) {
                meme.setPositiveScoreSum(meme.getPositiveScoreSum() + 1);
                meme.setNegativeScoreSum(meme.getNegativeScoreSum() - 1);
            } else {
                meme.setPositiveScoreSum(meme.getPositiveScoreSum() - 1);
                meme.setNegativeScoreSum(meme.getNegativeScoreSum() + 1);
            }
        }
    }
}
