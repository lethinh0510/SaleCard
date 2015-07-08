package com.lethinh.salecard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import utils.JSONParser;

/**
 * Created by Thinh on 06/07/2015.
 */
public class LoginActivity extends Activity {
    EditText edUserName,edPassword;
    Button btLogin;
    TextView tvSign,tvError;

    JSONParser jsonParser= new JSONParser();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserName=(EditText)findViewById(R.id.text_email);
        edPassword=(EditText)findViewById(R.id.text_pass);
        btLogin=(Button)findViewById(R.id.button_login);
        tvSign=(TextView)findViewById(R.id.textview_sign);
        tvError=(TextView)findViewById(R.id.test_error);
    }
    @Override
    protected void onResume() {
        super.onResume();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginStask().execute("http://192.168.1.82/salecard/login.php");
            }
        });
    }
    class LoginStask extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            List<NameValuePair> params= new ArrayList<>();
            params.add(new BasicNameValuePair("username","'"+ edUserName.getText().toString()+"'"));
            params.add(new BasicNameValuePair("password",edPassword.getText().toString()));
            JSONObject jsonAcc= jsonParser.makeHttpRequest(strings[0],"POST",params);
            Log.e("TAG",jsonAcc.toString());
            return jsonAcc;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                if(jsonObject.getInt("success")==1){
                    pref= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("username",edUserName.getText().toString());
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }else{
                    tvError.setText(jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
