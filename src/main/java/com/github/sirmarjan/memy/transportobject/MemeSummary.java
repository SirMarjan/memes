package com.github.sirmarjan.memy.transportobject;

import com.github.sirmarjan.memy.transportobject.ScoreState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemeSummary {

    long memeId;

    ScoreState scoreState;

    int negativeScoreSum;

    int positiveScoreSum;

    String title;

    String authorUsername;
}
