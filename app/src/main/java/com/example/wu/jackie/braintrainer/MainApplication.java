package com.example.wu.jackie.braintrainer;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;

public class MainApplication extends Application {

    public static final String
            KEY_PREF_DARK_THEME_SWITCH = "dark_theme_switch";
    private static int sThemeId;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static void setAppTheme(Boolean switchPref) {
        if(switchPref) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }



}
