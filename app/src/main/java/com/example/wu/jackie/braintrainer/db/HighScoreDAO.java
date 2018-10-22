package com.example.wu.jackie.braintrainer.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface HighScoreDAO {

    @Query("SELECT * FROM high_score ORDER BY player_score ASC")
    LiveData<List<HighScoreEntity>> loadAllHighScores();

    //Prolly dont need update for High Scores as it will be a list
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateHighScore(HighScoreEntity...highScores);

    @Query("DELETE FROM high_score")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(HighScoreEntity highScore);
}
