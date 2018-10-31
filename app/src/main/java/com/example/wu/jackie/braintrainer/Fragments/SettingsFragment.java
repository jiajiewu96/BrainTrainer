package com.example.wu.jackie.braintrainer.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.content.SharedPreferences;

import android.support.v7.preference.Preference;

import com.example.wu.jackie.braintrainer.MainApplication;
import com.example.wu.jackie.braintrainer.R;


public class SettingsFragment extends PreferenceFragmentCompat  {
    private SharedPreferences mSharedPreferences;
    private String sharedPrefFile = "com.example.wu.jackie.braintrainer.themesettings";
    public static final String
            KEY_PREF_DARK_THEME_SWITCH = "dark_theme_switch";
    public static final String
            KEY_PREF_SUMMARY = "summary";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefrences, rootKey);
        mSharedPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        Preference preference = this.findPreference(KEY_PREF_DARK_THEME_SWITCH);
        preference.setSummary(mSharedPreferences.getString(KEY_PREF_SUMMARY, getString(R.string.dark_theme_switch_summary_off)));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if((Boolean) newValue){
                    preference.setSummary(R.string.dark_theme_switch_summary_on);
                    SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
                    preferenceEditor.putString(KEY_PREF_SUMMARY,getString(R.string.dark_theme_switch_summary_on))
                            .putBoolean(KEY_PREF_DARK_THEME_SWITCH, true).apply();
                    Boolean switchPref = mSharedPreferences.getBoolean(KEY_PREF_DARK_THEME_SWITCH, false);
                    MainApplication.setAppTheme(switchPref);
                }else {
                    preference.setSummary(R.string.dark_theme_switch_summary_off);
                    SharedPreferences.Editor preferenceEditor =
                            mSharedPreferences.edit();
                    preferenceEditor.putString(KEY_PREF_SUMMARY, getString(R.string.dark_theme_switch_summary_off))
                            .putBoolean(KEY_PREF_DARK_THEME_SWITCH, false).apply();
                    Boolean switchPref = mSharedPreferences.getBoolean(KEY_PREF_DARK_THEME_SWITCH, false);
                    MainApplication.setAppTheme(switchPref);
                }
                return true;
            }
        });
    }

}
