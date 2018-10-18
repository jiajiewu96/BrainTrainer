package com.example.wu.jackie.braintrainer.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class HighScoreViewModel extends AndroidViewModel {
    private HighScoreRepository mRepository;
    private LiveData<List<HighScore>> mAllHighScores;

    public HighScoreViewModel(Application application){
        super(application);
        mRepository = new HighScoreRepository(application);
        mAllHighScores = mRepository.loadAllHighScores();
    }

    public LiveData<List<HighScore>> getAllHighScores(){
        return mAllHighScores;
    }

    public void insert(HighScore highScore){
        mRepository.insert(highScore);
    }
}
