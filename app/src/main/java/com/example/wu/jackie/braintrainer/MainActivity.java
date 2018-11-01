package com.example.wu.jackie.braintrainer;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wu.jackie.braintrainer.Fragments.AnswersFragment;
import com.example.wu.jackie.braintrainer.Fragments.HighScoreFragment;
import com.example.wu.jackie.braintrainer.Fragments.NumbersFragment;
import com.example.wu.jackie.braintrainer.Fragments.PlayAgainFragment;
import com.example.wu.jackie.braintrainer.Fragments.ResultFragment;
import com.example.wu.jackie.braintrainer.Fragments.SettingsFragment;
import com.example.wu.jackie.braintrainer.db.HighScoreEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyListener {

    public int score = 0;
    public int numberOfQuestions = 0;

    private NumbersFragment mNumbersFragment;
    private AnswersFragment mAnswersFragment;
    private PlayAgainFragment mPlayAgainFragment;
    private HighScoreFragment mHighScoreFragment;
    private SettingsFragment mSettingsFragment;

    private int num1, num2;
    private String playerName;

    private ArrayList<Integer> answersArrayList;

    Button highScoreButton;
    Button startGameButton;
    TextView timerTextView;
    TextView scoreTextView;
    EditText playerNameEditText;
    HighScoreEntity mHighScore;

    FragmentManager mFragmentManager;

    boolean gameActive;

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private int locationOfCorrectAnswer;

    QuestionGenerator mQuestionGenerator;
    private ResultFragment mResultFragment;

    private int countDownTimeInMillis, countDownIntervalInMillis;



    public static final String
            KEY_PREF_DARKTHEME_SWITCH = "dark_theme_switch";
    private String mCurrentTheme;
    private boolean switchPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameActive = false;

        mQuestionGenerator = new QuestionGenerator();

        timerTextView = (TextView) findViewById(R.id.timeTextView);
        timerTextView.setVisibility(View.INVISIBLE);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText("0/0");
        scoreTextView.setVisibility(View.INVISIBLE);
        playerNameEditText = (EditText) findViewById(R.id.playerNameEditText);

        highScoreButton = (Button) findViewById(R.id.highScoreButton);

        startGameButton = (Button) findViewById(R.id.startGameButton);

        PreferenceManager.setDefaultValues(this, R.xml.prefrences, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mPlayAgainFragment!=null){
            addPlayAgainFragment();
        }
        else if(startGameButton.getVisibility() == View.INVISIBLE){
            playerNameEditText.setVisibility(View.VISIBLE);
            startGameButton.setVisibility(View.VISIBLE);
        }else{
            super.onBackPressed();
        }
        highScoreButton.setVisibility(View.VISIBLE);
        removeHighScoreFragment();

    }

    @Override
    public void onResume(){
        super.onResume();
    }


    //onClick method that generates Initial Question.
    public void generateInitialQuestion(View view) {
        gameActive = true;

        playerName = playerNameEditText.getText().toString();

        playerNameEditText.setVisibility(View.INVISIBLE);
        highScoreButton.setVisibility(View.INVISIBLE);
        generateQuestionVariables();

        addNumberFragment();
        addAnswerFragment();

        startTimer();
        resetScore();

        if (startGameButton.getVisibility() == View.VISIBLE) {
            startGameButton.setVisibility(View.GONE);
        }
    }


    private void resetScore() {
        scoreTextView.setVisibility(View.VISIBLE);
        scoreTextView.setText("0/0");
        score = 0;
        numberOfQuestions = 0;
    }

    private void startTimer() {
        timerTextView.setVisibility(View.VISIBLE);
        timerTextView.setBackgroundResource(android.R.color.transparent);
        countDownTimeInMillis = 30100;
        countDownIntervalInMillis = 1000;
        timerTextView.setText(String.valueOf((countDownTimeInMillis - 100) / 1000));
        new CountDownTimer(countDownTimeInMillis, countDownIntervalInMillis) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.format("%ss", String.valueOf(l / countDownIntervalInMillis)));
                if (l <= 3100) {
                    timerTextView.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onFinish() {
                timerTextView.setText(R.string.finished_time_text);
                int percent = (int)((score * 100f)/numberOfQuestions);
                mHighScore = new HighScoreEntity(playerName, score, percent);
                gameActive = false;
                setGameOverScreen();
            }
        }.start();

    }

    private void setGameOverScreen() {
        highScoreButton.setVisibility(View.VISIBLE);
        removeNumberFragment();
        removeResultFragment();
        removeAnswerFragment();
        addPlayAgainFragment();
        timerTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
    }

    private void removeAnswerFragment() {
        mAnswersFragment = (AnswersFragment) getSupportFragmentManager().findFragmentByTag("fragAnswer");
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if(mAnswersFragment !=null){
            ft.remove(mAnswersFragment).commit();
        }
    }

    private void removeResultFragment() {
        mResultFragment = (ResultFragment) getSupportFragmentManager().findFragmentByTag("fragResult");
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (mResultFragment != null) {
            ft.remove(mResultFragment).commit();
        }
    }

    private void removeNumberFragment() {
        mNumbersFragment = (NumbersFragment) getSupportFragmentManager().findFragmentByTag("fragNumbers");
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (mNumbersFragment != null) {
            ft.remove(mNumbersFragment).commit();
        }
    }

    //generates question variables
    private void generateQuestionVariables() {
        num1 = mQuestionGenerator.generateNumber();
        num2 = mQuestionGenerator.generateNumber();

        answersArrayList = mQuestionGenerator.generateAnswers(num1 + num2);
        locationOfCorrectAnswer = mQuestionGenerator.getLocationOfCorrectAnswer();

    }

    //Adds numberFragment
    private void addNumberFragment() {
        mNumbersFragment = new NumbersFragment();
        numberFragmentExtras();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.numberContainer, mNumbersFragment, "fragNumbers");
        ft.commit();
    }


    //Data sent to numbersFragment
    private void numberFragmentExtras() {
        mNumbersFragment.setNumbers(num1, num2);
    }

    //Adds the answerFragment
    private void addAnswerFragment() {
        mAnswersFragment = new AnswersFragment();
        answerFragmentExtras();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.answerContainer, mAnswersFragment, "fragAnswer");
        ft.commit();

    }

    //Data sent to Answer Fragment
    private void answerFragmentExtras() {
        mAnswersFragment.addTwoNumbersInFragmentB(num1, num2);
        mAnswersFragment.setLocationofCorrectAnswer(locationOfCorrectAnswer);
        mAnswersFragment.setAnswersArrayList(answersArrayList);
    }

    //Adds the resultFragment
    private void addResultFragment() {
        mResultFragment = new ResultFragment();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.resultContainer, mResultFragment, "fragResult").commit();
    }

    @Override
    public void checkAnswer(boolean isCorrectAnswer) {

        mResultFragment = (ResultFragment) getSupportFragmentManager().findFragmentByTag("fragResult");
        if (mResultFragment != null) {
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.remove(mResultFragment).commit();
        }
        addResultFragment();
        Log.e(LOG_TAG, Boolean.toString(isCorrectAnswer));
        if (isCorrectAnswer) {
            score++;
            mResultFragment.setResult(isCorrectAnswer);
        } else {
            mResultFragment.setResult(isCorrectAnswer);
        }
        numberOfQuestions++;
        scoreTextView.setText(String.format("%s/%s", Integer.toString(score), Integer.toString(numberOfQuestions)));
        refreshQuestion();

    }

    private void addPlayAgainFragment() {
        mPlayAgainFragment = new PlayAgainFragment();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        mPlayAgainFragment.sendScore(score, numberOfQuestions);
        ft.add(R.id.playAgainContainer, mPlayAgainFragment, "fragPlayAgain");
        ft.commit();
    }


    //PlayAgainButton on click
    @Override
    public void playAgain() {
        Log.e(LOG_TAG, "play again clicked");
        removePlayAgainFragment();
        generateQuestionVariables();

        addNumberFragment();
        startTimer();
        resetScore();
        highScoreButton.setVisibility(View.INVISIBLE);
    }



    private void refreshQuestion() {
        //Toast.makeText(this, "Making new question", Toast.LENGTH_SHORT).show();
        Log.e(LOG_TAG, "refreshing Questions");
        generateQuestionVariables();
        refreshNumberFragment();
        refreshAnswerFragment();
    }

    private void refreshAnswerFragment() {
        mAnswersFragment = new AnswersFragment();
        answerFragmentExtras();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.answerContainer, mAnswersFragment, "fragAnswer").commit();
//        addAnswerFragment();
        Log.e(LOG_TAG, "Refreshing Answer Fragment");

    }

    private void refreshNumberFragment() {
        mNumbersFragment = new NumbersFragment();
        numberFragmentExtras();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.numberContainer, mNumbersFragment, "fragNumbers").commit();
//        addNumberFragment();
        Log.e(LOG_TAG, "Refreshing Number Fragment");
    }


    //Show high score on click
    public void showHighScore(View view) {
        Log.i(LOG_TAG, "Adding High Score Fragment");
        highScoreFragmentOperations();
        removeNumberFragment();
        removeResultFragment();
    }

    private void highScoreFragmentOperations() {
        mHighScoreFragment = new HighScoreFragment();
        mAnswersFragment = (AnswersFragment) getSupportFragmentManager().findFragmentByTag("fragAnswers");
        mPlayAgainFragment =(PlayAgainFragment) getSupportFragmentManager().findFragmentByTag("fragPlayAgain");
        if (mAnswersFragment != null) {
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.remove(mAnswersFragment).commit();

        }else if(mPlayAgainFragment!= null){
            removePlayAgainFragment();
        }
        addHighScoreFragment();

    }

    private void removePlayAgainFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.remove(mPlayAgainFragment).commit();
    }

    private void addHighScoreFragment() {
        if(highScoreButton.getVisibility() == View.VISIBLE){
            highScoreButton.setVisibility(View.INVISIBLE);
        }
        if(startGameButton.getVisibility() == View.VISIBLE){
            playerNameEditText.setVisibility(View.INVISIBLE);
            startGameButton.setVisibility(View.INVISIBLE);
        }

        mHighScoreFragment = new HighScoreFragment();
        highScoreFragmentExtras();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.highScoreContainer, mHighScoreFragment, "fragHighScore")
                .addToBackStack("fragHighScore").commit();
    }

    private void removeHighScoreFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.remove(mHighScoreFragment).commit();
    }

    private void highScoreFragmentExtras() {
        Log.i(LOG_TAG, "High score extras");
        if(mHighScore!=null) {
            mHighScoreFragment.sendHighScore(mHighScore);
        }
    }
}
