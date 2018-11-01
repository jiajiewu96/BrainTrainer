package com.example.wu.jackie.braintrainer.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.content.SharedPreferences;
import android.support.v7.preference.Preference;


import com.example.wu.jackie.braintrainer.MainApplication;
import com.example.wu.jackie.braintrainer.R;


public class SettingsFragment extends PreferenceFragmentCompat {
    private SharedPreferences mSharedPreferences;
    public static final String
            KEY_PREF_DARK_THEME_SWITCH = "dark_theme_switch";
    public static final String
            KEY_PREF_SUMMARY = "summary";
    private Preference mThemePreference;
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;
    private Boolean mDarkThemeSwitch;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefrences, rootKey);
        String sharedPrefFile = "com.example.wu.jackie.braintrainer.themesettings";
        mSharedPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        mThemePreference = this.findPreference(KEY_PREF_DARK_THEME_SWITCH);
        mDarkThemeSwitch = mSharedPreferences.getBoolean(KEY_PREF_DARK_THEME_SWITCH, false);
        mThemePreference.setSummary(getString(R.string.dark_theme_switch_summary_description));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                switch (s) {
                    case KEY_PREF_DARK_THEME_SWITCH:
                        mDarkThemeSwitch = sharedPreferences.getBoolean(KEY_PREF_DARK_THEME_SWITCH, false);
                        if (mDarkThemeSwitch) {
                            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
                            preferenceEditor.putBoolean(KEY_PREF_DARK_THEME_SWITCH, true).apply();
                            MainApplication.setAppTheme(mDarkThemeSwitch);

                        } else {
                            SharedPreferences.Editor preferenceEditor =
                                    sharedPreferences.edit();
                            preferenceEditor.putBoolean(KEY_PREF_DARK_THEME_SWITCH, false).apply();
                            MainApplication.setAppTheme(mDarkThemeSwitch);
                        }
                        getActivity().recreate();
                        break;
                }
            }

        };

    }



    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
        super.onPause();
    }
}
