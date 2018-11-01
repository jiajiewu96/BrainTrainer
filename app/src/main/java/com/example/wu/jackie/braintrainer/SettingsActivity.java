package com.example.wu.jackie.braintrainer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;

import com.example.wu.jackie.braintrainer.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    public static final String
            KEY_PREF_DARK_THEME_SWITCH = "dark_theme_switch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


}
