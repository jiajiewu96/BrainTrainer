package com.example.wu.jackie.braintrainer.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wu.jackie.braintrainer.R;

public class NumbersFragment extends Fragment {

    private static final String TAG = NumbersFragment.class.getSimpleName();

    TextView problemNums;

    private int num1 = 0,num2 = 0;

    private String LOG_TAG = NumbersFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.numbers_fragment, container, false);
        problemNums = (TextView)view.findViewById(R.id.problem_numbers);

        setTVText();

        return view;
    }

    public void setTVText() {
        problemNums.setText(String.format("%s + %s", String.valueOf(num1), String.valueOf(num2)));
    }

    //Sets numbers in NumbersFragment to the values created in MainActivity
    public void setNumbers(int n1, int n2) {
        this.num1 = n1;
        this.num2 = n2;
    }
}
