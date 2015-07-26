package com.lethinh.salecard;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Thinh on 26/07/2015.
 */
public class DetailCardActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_card);
        getActionBar().setHomeButtonEnabled(true);
    }
}
