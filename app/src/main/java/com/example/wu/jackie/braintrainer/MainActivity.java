package com.example.wu.jackie.braintrainer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyListener {

    public int score = 0;
    public int numberOfQuestions = 0;

    Button startGameButton;
    private NumbersFragment mNumbersFragment;
    private AnswersFragment mAnswersFragment;
    private PlayAgainFragment mPlayAgainFragment;

    private int num1, num2;

    private ArrayList<Integer> answersArrayList;

    TextView timerTextView;
    TextView scoreTextView;

    boolean gameActive;

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private int locationOfCorrectAnswer;

    QuestionGenerator mQuestionGenerator;
    private ResultFragment mResultFragment;

    private int countDownTimeInMillis, countDownIntervalInMillis;


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

        startGameButton = (Button) findViewById(R.id.start_game_button);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //onClick method that generates Initial Question.
    public void generateInitialQuestion(View view) {
        gameActive = true;

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
        countDownTimeInMillis = 10100;
        countDownIntervalInMillis = 1000;
        timerTextView.setText("10s");
        new CountDownTimer(countDownTimeInMillis, countDownIntervalInMillis) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf( l/ countDownIntervalInMillis) + "s");
            }

            @Override
            public void onFinish() {
                gameActive = false;
                setGameOverScreen();
            }
        }.start();

    }

    private void setGameOverScreen() {
        replaceAnswerFragWithPlayAgainFrag();
        removeNumberFragment();
        removeResultFragment();
        timerTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
    }

    private void removeResultFragment() {
        mResultFragment = (ResultFragment) getFragmentManager().findFragmentByTag("fragResult");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(mResultFragment).commit();
    }

    private void removeNumberFragment() {
        mNumbersFragment = (NumbersFragment) getFragmentManager().findFragmentByTag("fragNumbers");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(mNumbersFragment).commit();
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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.numberContainer, mNumbersFragment, "fragNumbers");
        transaction.commit();
    }


    //Data sent to numbersFragment
    private void numberFragmentExtras() {
        mNumbersFragment.setNumbers(num1, num2);
    }

    //Adds the answerFragment
    private void addAnswerFragment() {
        mAnswersFragment = new AnswersFragment();
        answerFragmentExtras();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.answerContainer, mAnswersFragment, "fragAnswer");
        transaction.commit();

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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.resultContainer, mResultFragment, "fragResult").commit();
    }

    @Override
    public void checkAnswer(boolean isCorrectAnswer) {

        mResultFragment = (ResultFragment) getFragmentManager().findFragmentByTag("fragResult");
        if (mResultFragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
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
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        refreshQuestion();

    }

    private void replaceAnswerFragWithPlayAgainFrag() {
        mPlayAgainFragment = new PlayAgainFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        mPlayAgainFragment.sendScore(score, numberOfQuestions);
        ft.replace(R.id.answerContainer, mPlayAgainFragment, "fragPlayAgain");
        ft.commit();
    }


    //PlayAgainButton on click
    @Override
    public void playAgain() {
        Log.e(LOG_TAG, "play again clicked");
        replacePlayAgainFrag();
        generateQuestionVariables();
        addNumberFragment();
        startTimer();
        resetScore();
    }

    private void replacePlayAgainFrag() {
        mAnswersFragment = new AnswersFragment();
        answerFragmentExtras();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.answerContainer, mAnswersFragment, "fragAnswer").commit();
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
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.answerContainer, mAnswersFragment, "fragAnswer").commit();
//        addAnswerFragment();
        Log.e(LOG_TAG, "Refreshing Answer Fragment");

    }

    private void refreshNumberFragment() {
        mNumbersFragment = new NumbersFragment();
        numberFragmentExtras();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.numberContainer, mNumbersFragment, "fragNumbers").commit();
//        addNumberFragment();
        Log.e(LOG_TAG, "Refreshing Number Fragment");
    }

}
