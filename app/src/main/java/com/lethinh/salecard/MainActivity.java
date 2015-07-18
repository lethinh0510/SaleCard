package com.lethinh.salecard;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lethinh.adapters.NavDrawerAdpter;
import com.lethinh.utils.DBHelp;
import com.lethinh.utils.NavDrawerItem;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private String []mPlantTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    ArrayList<NavDrawerItem> itemsDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelp bd= new DBHelp(getApplicationContext());
        SQLiteDatabase database= bd.getWritableDatabase();
        itemsDrawer= new ArrayList<NavDrawerItem>();
        mPlantTitles=getResources().getStringArray(R.array.planets_array);

        itemsDrawer.add(new NavDrawerItem(mPlantTitles[0],R.drawable.logo_viettel,1));
        itemsDrawer.add(new NavDrawerItem(mPlantTitles[1],R.drawable.logo_mobifone,2));
        itemsDrawer.add(new NavDrawerItem(mPlantTitles[2],R.drawable.logo_vinaphone,4));
        itemsDrawer.add(new NavDrawerItem(mPlantTitles[3],R.drawable.logo_logout,4));

        mDrawerLayout= (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mPlantTitles));
        mDrawerList.setAdapter(new NavDrawerAdpter(getApplicationContext(),itemsDrawer));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(),SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
