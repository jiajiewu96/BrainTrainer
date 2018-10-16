package com.example.wu.jackie.braintrainer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class AnswersFragment extends Fragment implements View.OnClickListener{
    Button answerButton1;
    Button answerButton2;
    Button answerButton3;
    Button answerButton4;

    private int mResult;

    private String LOG_TAG = AnswersFragment.class.getSimpleName();
    private ArrayList<Integer> mAnswerList;
    private int locationOfCorrectAnswer;
    private boolean mCorrectAnswerClicked;
    private boolean mIsCorrect;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");

        View view = inflater.inflate(R.layout.answers_fragment,container,false);

        answerButton1 = view.findViewById(R.id.answerButton1);
        answerButton2 = view.findViewById(R.id.answerButton2);
        answerButton3 = view.findViewById(R.id.answerButton3);
        answerButton4 = view.findViewById(R.id.answerButton4);

        setAnswerButtons();

        answerButton1.setOnClickListener(this);
        answerButton2.setOnClickListener(this);
        answerButton3.setOnClickListener(this);
        answerButton4.setOnClickListener(this);

        return view;
    }

    public void setAnswerButtons(){

        Log.i(AnswersFragment.class.getSimpleName(), mAnswerList.toString());

        answerButton1.setText(Integer.toString(mAnswerList.get(0)));
        answerButton2.setText(Integer.toString(mAnswerList.get(1)));
        answerButton3.setText(Integer.toString(mAnswerList.get(2)));
        answerButton4.setText(Integer.toString(mAnswerList.get(3)));
    }

    public void addTwoNumbersInFragmentB(int num1, int num2) {
        mResult=0;
        mResult = num1 + num2;
    }

    @Override
    public void onClick(View v) {
        mIsCorrect = checkCorrectAnswer(v);

        MyListener myListener = (MyListener) getActivity();
        myListener.checkAnswer(mIsCorrect);
    }

    private boolean checkCorrectAnswer(View v) {
        mCorrectAnswerClicked = v.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer));
        return mCorrectAnswerClicked;
    }

    public void setAnswersArrayList(ArrayList<Integer> answersArrayList) {
        this.mAnswerList = answersArrayList;
    }

    public void setLocationofCorrectAnswer(int correctAnswer) {
        this.locationOfCorrectAnswer = correctAnswer;
    }
}
