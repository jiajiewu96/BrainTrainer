package com.example.wu.jackie.braintrainer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class QuestionGenerator {


    private static QuestionGenerator instance;

    private int num;
    private ArrayList<Integer> answerArrayList = new ArrayList<>();
    private int locationOfCorrectAnswer;

    private Random mRandom;

    QuestionGenerator(){
        if (instance == null){
            instance = this;
        }
    }

    public int getLocationOfCorrectAnswer() {
        return locationOfCorrectAnswer;
    }

    public int generateNumber(){
        mRandom = new Random();
        num = mRandom.nextInt(40);
        return num;
    }

    public ArrayList<Integer> generateAnswers(int answer){
        answerArrayList.clear();
        mRandom = new Random();
        locationOfCorrectAnswer = mRandom.nextInt(4);
        int incorrectAnswer;


        for (int i = 0; i <4; i++){
            if(i == locationOfCorrectAnswer) {

                answerArrayList.add(answer);
            }else {
                incorrectAnswer = mRandom.nextInt(40);

                while (incorrectAnswer == answer){
                    incorrectAnswer = mRandom.nextInt(40);
                }

                answerArrayList.add(incorrectAnswer);
            }
        }



        return (answerArrayList);


    }
}
