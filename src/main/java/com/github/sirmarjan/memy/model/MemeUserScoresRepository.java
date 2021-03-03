package com.github.sirmarjan.memy.model;

import com.github.sirmarjan.memy.model.entities.MemeUserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemeUserScoresRepository extends JpaRepository<MemeUserScore, MemeUserScore.MemUserScoreId> {

    @Query("SELECT mus FROM MemeUserScore mus WHERE mus.id.userId = :userId AND mus.id.memeId = :memeId")
    Optional<MemeUserScore> getMUSByUserAndMemeId(@Param("userId") long UserId,
                                                  @Param("memeId") long memeId);
}
