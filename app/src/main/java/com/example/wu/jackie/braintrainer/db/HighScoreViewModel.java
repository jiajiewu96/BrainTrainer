package com.example.wu.jackie.braintrainer.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class HighScoreViewModel extends AndroidViewModel {
    private HighScoreRepository mRepository;
    private LiveData<List<HighScoreEntity>> mAllHighScores;

    public HighScoreViewModel(Application application){
        super(application);
        mRepository = new HighScoreRepository(application);
        mAllHighScores = mRepository.loadAllHighScores();
    }

    public LiveData<List<HighScoreEntity>> getAllHighScores(){
        return mAllHighScores;
    }

    public void insert(HighScoreEntity highScore){
        mRepository.insert(highScore);
    }
}
