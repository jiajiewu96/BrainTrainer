package com.example.wu.jackie.braintrainer.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {HighScore.class}, version = 1)
public abstract class HighScoreDB extends RoomDatabase {

    public abstract HighScoreDAO mHighScoreDAO();
    private static volatile HighScoreDB INSTANCE;

    static HighScoreDB getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (HighScoreDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HighScoreDB.class, "high_score_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
