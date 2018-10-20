package com.example.wu.jackie.braintrainer.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "high_score")
public class HighScore {
    @NonNull
    @PrimaryKey
    private String playerName;

    @ColumnInfo(name = "player_score")
    private int playerScore;

    @ColumnInfo(name = "percent_questions_correct")
    private int percentQuestionsCorrect;

    public HighScore(@NonNull String playerName, int playerScore, int percentQuestionsCorrect){
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.percentQuestionsCorrect = percentQuestionsCorrect;
    }

    public void setPlayerName(@NonNull String playerName){
        this.playerName = playerName;
    }

    public void setPlayerScore(int playerScore){
        this.playerScore = playerScore;
    }

    public void setPercentQuestionsCorrect(int percentQuestionsCorrect){
        this.percentQuestionsCorrect = percentQuestionsCorrect;
    }

    @NonNull
    public String getPlayerName(){
        return this.playerName;
    }

    public int getPlayerScore(){
        return this.playerScore;
    }

    public int getPercentQuestionsCorrect(){
        return this.percentQuestionsCorrect;
    }
}
