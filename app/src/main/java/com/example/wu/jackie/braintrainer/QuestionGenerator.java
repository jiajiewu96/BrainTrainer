package com.example.wu.jackie.braintrainer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class QuestionGenerator {


    private static QuestionGenerator instance;

    private int num, randBound;
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
        randBound = 40;
        mRandom = new Random();
        num = mRandom.nextInt(randBound);
        return num;
    }

    //Generates correct answer along side incorrect answers
    public ArrayList<Integer> generateAnswers(int answer){
        answerArrayList.clear();
        mRandom = new Random();
        locationOfCorrectAnswer = mRandom.nextInt(4);
        int incorrectAnswer;


        for (int i = 0; i <4; i++){
            if(i == locationOfCorrectAnswer) {

                answerArrayList.add(answer);
            }else {
                incorrectAnswer = mRandom.nextInt(randBound * 2);

                while (incorrectAnswer == answer){
                    incorrectAnswer = mRandom.nextInt(randBound * 2);
                }

                answerArrayList.add(incorrectAnswer);
            }
        }



        return (answerArrayList);


    }
}
