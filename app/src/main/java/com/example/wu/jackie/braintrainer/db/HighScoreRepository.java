package com.example.wu.jackie.braintrainer.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class HighScoreRepository {
    private HighScoreDAO mHighScoreDAO;
    private LiveData<List<HighScore>> mAllHighScores;

    HighScoreRepository(Application application){
        HighScoreDB db = HighScoreDB.getDatabase(application);
        mHighScoreDAO = db.mHighScoreDAO();
        mAllHighScores = mHighScoreDAO.loadAllHighScores();
    }

    LiveData<List<HighScore>> loadAllHighScores(){
        return mAllHighScores;
    }

    public void insert(HighScore highScore){
        new insertAsyncTask(mHighScoreDAO).execute(highScore);
    }

    private static class insertAsyncTask extends AsyncTask<HighScore, Void, Void>{

        private HighScoreDAO mAsyncTaskDao;

        insertAsyncTask(HighScoreDAO dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(HighScore... highScores) {
            mAsyncTaskDao.insert(highScores[0]);
            return null;
        }
    }

}