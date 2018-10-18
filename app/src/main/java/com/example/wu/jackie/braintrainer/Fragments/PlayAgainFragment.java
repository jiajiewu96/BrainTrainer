package com.example.wu.jackie.braintrainer.Fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wu.jackie.braintrainer.MyListener;
import com.example.wu.jackie.braintrainer.R;

public class PlayAgainFragment extends Fragment implements View.OnClickListener{

    Button playAgainButton;
    TextView scoreTextView, gameOverTextView;

    int score, numberOfQuestions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_again_fragment, container, false);

        scoreTextView = view.findViewById(R.id.scoreTextView);
        playAgainButton = view.findViewById(R.id.playAgainButton);
        gameOverTextView = view.findViewById(R.id.gameOverTextView);

        playAgainButton.setOnClickListener(this);
        scoreTextView.setText("Your Score:" + "\n" + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

        return view;
    }

    @Override
    public void onClick(View v) {
        MyListener myListener = (MyListener) getActivity();
        myListener.playAgain();
    }

    public void sendScore(int n1, int n2) {
        this.score = n1;
        this.numberOfQuestions =n2;
    }
}
