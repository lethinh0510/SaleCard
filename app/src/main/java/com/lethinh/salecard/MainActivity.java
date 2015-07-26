package com.lethinh.salecard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lethinh.adapters.NavDrawerAdpter;
import com.lethinh.utils.DBHelp;
import com.lethinh.utils.JSONParser;
import com.lethinh.utils.LinkUtils;
import com.lethinh.utils.NavDrawerItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private String []mPlantTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    ArrayList<NavDrawerItem> itemsDrawer;
    private ListCardFragment cardFragment;
    String fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlantTitles=getResources().getStringArray(R.array.planets_array);

        cardFragment= new ListCardFragment();
        Bundle bundle= new Bundle();
        bundle.putString("PRODUCT", mPlantTitles[0]);
        cardFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame,cardFragment).commit();
        fragment=mPlantTitles[0];


        DBHelp bd= new DBHelp(getApplicationContext());
        SQLiteDatabase database= bd.getWritableDatabase();
        itemsDrawer= new ArrayList<NavDrawerItem>();


        itemsDrawer.add(new NavDrawerItem(mPlantTitles[0],R.drawable.logo_viettel,1));
        itemsDrawer.add(new NavDrawerItem(mPlantTitles[1],R.drawable.logo_mobifone,2));
        itemsDrawer.add(new NavDrawerItem(mPlantTitles[2],R.drawable.logo_vinaphone,4));
        itemsDrawer.add(new NavDrawerItem(mPlantTitles[3],R.drawable.logo_logout,0));

        mDrawerLayout= (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new NavDrawerAdpter(getApplicationContext(),itemsDrawer));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cardFragment= new ListCardFragment();
                Bundle bundle= new Bundle();
                bundle.putString("PRODUCT", mPlantTitles[i]);
                cardFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, cardFragment).commit();
                fragment=mPlantTitles[i];
                getActionBar().setTitle(fragment);
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
        }else if(id==R.id.action_add_card){
            showDialogAddCard();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showDialogAddCard(){

        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_card);
        dialog.setTitle("Add Card");
        final Spinner spinnerProduct= (Spinner)dialog.findViewById(R.id.spinner_product);
        String []producCard={mPlantTitles[0],mPlantTitles[1],mPlantTitles[2]};
        ArrayAdapter<CharSequence> adapter= new ArrayAdapter<CharSequence>(
         this,android.R.layout.simple_spinner_item,producCard);
        spinnerProduct.setAdapter(adapter);

        final Spinner spinnerPrice= (Spinner)dialog.findViewById(R.id.spinner_price);
        Integer[]priceArray=new Integer[]{20,50,100};
        ArrayAdapter<Integer> adapter1= new ArrayAdapter<Integer>(
               this,android.R.layout.simple_spinner_item,priceArray);
        spinnerPrice.setAdapter(adapter1);
        Button btOk= (Button)dialog.findViewById(R.id.button_ok);
        final EditText edCode= (EditText)dialog.findViewById(R.id.edit_code);
        final EditText edSeri= (EditText)dialog.findViewById(R.id.edit_seri);
        Button btCancel=(Button)dialog.findViewById(R.id.button_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product= spinnerProduct.getSelectedItem().toString().toLowerCase();
                Log.e("TAG",product);
                String code= edCode.getText().toString();
                String seri= edSeri.getText().toString();
                String price= spinnerPrice.getSelectedItem().toString();
                new InsertCardTask().execute(LinkUtils.URL_INSERT, product, code,seri,price);
                Toast.makeText(getApplicationContext(),"Add Success",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                cardFragment= new ListCardFragment();
                Bundle bundle= new Bundle();
                bundle.putString("PRODUCT", fragment);
                cardFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, cardFragment).commit();

            }
        });


        dialog.show();
    }
    private class InsertCardTask extends AsyncTask<String,Void,JSONObject>{
        private JSONParser parser= new JSONParser();


        @Override
        protected JSONObject doInBackground(String... strings) {
            List<NameValuePair> params= new ArrayList<>();
            params.add(new BasicNameValuePair("product",strings[1]));
            params.add(new BasicNameValuePair("code",strings[2]));
            params.add(new BasicNameValuePair("seri",strings[3]));
            params.add(new BasicNameValuePair("price",strings[4]));
            JSONObject jsonObject= parser.makeHttpRequest(strings[0],"POST",params);
            return jsonObject;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.e("JSON:",jsonObject.toString());
        }
    }

}
