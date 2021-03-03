package com.github.sirmarjan.memy.service.implementation;

import com.github.sirmarjan.memy.model.MemeUserScoresRepository;
import com.github.sirmarjan.memy.model.MemesRepository;
import com.github.sirmarjan.memy.model.entities.Meme;
import com.github.sirmarjan.memy.model.entities.MemeUserScore;
import com.github.sirmarjan.memy.service.LoadMemeSummaryService;
import com.github.sirmarjan.memy.service.exception.EntityNotFoundException;
import com.github.sirmarjan.memy.service.exception.MemeNotFoundException;
import com.github.sirmarjan.memy.transportobject.MemeSummary;
import com.github.sirmarjan.memy.transportobject.ScoreState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter(AccessLevel.PROTECTED)
public class LoadMemeSummaryServiceImpl implements LoadMemeSummaryService {

    @NonNull
    private final MemeUserScoresRepository memeUserScoresRe;

    @NonNull
    private final MemesRepository memesRe;

    @Autowired
    public LoadMemeSummaryServiceImpl(@NonNull final MemesRepository memesRe,
                                      @NonNull final MemeUserScoresRepository memeUserScoresRe) {
        this.memesRe = memesRe;
        this.memeUserScoresRe = memeUserScoresRe;
    }

    @Override
    @NonNull
    public MemeSummary getMemeSummary(final long memeId, final Long userId) throws EntityNotFoundException {
        final var memeOp = getMemesRe().findById(memeId);
        return memeOp.map(meme -> createMemeSummary(meme, userId)).orElseThrow(MemeNotFoundException::new);
    }

    @Override
    @NonNull
    public Page<MemeSummary> getMemeSummaryPageByContainsInTitle(@NonNull final String titlePart, final Long userId, final int page, final int size){
        final var memeOp = getMemesRe().findAllByTitleContains(titlePart, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime")));
        return memeOp.map(meme -> createMemeSummary(meme, userId));
    }

    @Override
    @NonNull
    public  Page<MemeSummary> getMemeSummaryPage(final Long userId, final int page, final int size) {
        final var memes = getMemesRe().findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime")));
        return memes.map(meme -> createMemeSummary(meme, userId));
    }

    @NonNull
    private ScoreState convertBoolScoreState(final boolean positiveScore) {
        return positiveScore ? ScoreState.POSITIVE : ScoreState.NEGATIVE;
    }

    @NonNull
    private MemeSummary createMemeSummary(@NonNull final Meme meme, final Long userId) {
        final var memeSummaryBuilder = MemeSummary.builder();
        final var memeUserScoreOp = Optional.ofNullable(userId).flatMap(
                userIdValue -> getMemeUserScoresRe().findById(new MemeUserScore.MemUserScoreId(meme.getId(), userId))
        );
        final var author = meme.getAuthor();
        if (author != null) {
            memeSummaryBuilder.authorUsername(author.getUsername());
        }
        memeSummaryBuilder
                .title(meme.getTitle())
                .memeId(meme.getId())
                .negativeScoreSum(meme.getNegativeScoreSum())
                .positiveScoreSum(meme.getPositiveScoreSum());
        memeUserScoreOp.ifPresentOrElse(
                mus -> memeSummaryBuilder.scoreState(convertBoolScoreState(mus.isPositiveScore())),
                () -> memeSummaryBuilder.scoreState(ScoreState.NONE));
        return memeSummaryBuilder.build();
    }
}
