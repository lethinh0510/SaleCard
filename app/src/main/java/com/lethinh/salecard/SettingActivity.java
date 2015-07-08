package com.lethinh.salecard;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Thinh on 08/07/2015.
 */
public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
