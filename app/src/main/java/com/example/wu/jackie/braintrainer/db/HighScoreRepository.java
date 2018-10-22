package com.example.wu.jackie.braintrainer.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class HighScoreRepository {
    private HighScoreDAO mHighScoreDAO;
    private LiveData<List<HighScoreEntity>> mAllHighScores;

    HighScoreRepository(Application application){
        HighScoreDB db = HighScoreDB.getDatabase(application);
        mHighScoreDAO = db.mHighScoreDAO();
        mAllHighScores = mHighScoreDAO.loadAllHighScores();
    }

    LiveData<List<HighScoreEntity>> loadAllHighScores(){
        return mAllHighScores;
    }

    public void deleteAll(){mHighScoreDAO.deleteAll();}

    public void insert(HighScoreEntity highScore){
        new insertAsyncTask(mHighScoreDAO).execute(highScore);
    }

    private static class insertAsyncTask extends AsyncTask<HighScoreEntity, Void, Void>{

        private HighScoreDAO mAsyncTaskDao;

        insertAsyncTask(HighScoreDAO dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(HighScoreEntity... highScores) {
            mAsyncTaskDao.insert(highScores[0]);
            return null;
        }
    }

}