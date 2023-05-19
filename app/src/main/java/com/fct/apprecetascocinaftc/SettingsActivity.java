package com.fct.apprecetascocinaftc;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Switch;



public class SettingsActivity extends PreferenceActivity {


    Switch themeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_preferences);

    }
}