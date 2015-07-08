package com.lethinh.salecard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {
   SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread splash = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                if(pref.getString("username","").equals("")){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }


                finish();
            }
        };
        splash.start();
    }




}
