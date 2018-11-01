package com.example.wu.jackie.braintrainer;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;

public class MainApplication extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static void setAppTheme(Boolean switchPref) {
            AppCompatDelegate.setDefaultNightMode(switchPref? AppCompatDelegate.MODE_NIGHT_YES:AppCompatDelegate.MODE_NIGHT_NO);

    }



}
