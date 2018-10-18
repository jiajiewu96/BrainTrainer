package com.example.wu.jackie.braintrainer.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wu.jackie.braintrainer.R;

public class ResultFragment extends Fragment {

    TextView resultTextView;
    private boolean correctAnswer  ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment,container,false);

        resultTextView = (TextView)view.findViewById(R.id.resultTextView);

        if (correctAnswer){
            resultTextView.setText(R.string.correct_text_view_text);
        }else {
            resultTextView.setText(R.string.incorrect_text_view_text);
        }

        return view;
    }

    public void setResult(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
