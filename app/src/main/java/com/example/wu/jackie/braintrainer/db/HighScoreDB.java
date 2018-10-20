package com.example.wu.jackie.braintrainer.db;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
                            .addCallback(sRoomDataBaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDataBaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{

        private final HighScoreDAO mDAO;

        PopulateDbAsync(HighScoreDB db){
            mDAO = db.mHighScoreDAO();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDAO.deleteAll();

            return null;
        }
    }
}
