package com.lethinh.salecard;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lethinh.utils.Card;
import com.lethinh.utils.JSONParser;
import com.lethinh.utils.LinkUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thinh on 26/07/2015.
 */
public class DetailCardActivity extends Activity {
    private TextView tvId,tvCreateAt,tvSeri,tvCode,tvPrice,tvProduct;
    private Button btSell, btCancel;
    private EditText etNote;
    Card card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_card);
        getActionBar().setHomeButtonEnabled(true);
        tvId=(TextView)findViewById(R.id.text_id);
        tvCreateAt=(TextView)findViewById(R.id.text_created_at);
        tvCode=(TextView)findViewById(R.id.text_code);
        tvSeri=(TextView)findViewById(R.id.text_seri);
        tvPrice=(TextView)findViewById(R.id.text_price);
        tvProduct=(TextView)findViewById(R.id.text_product);
        etNote=(EditText)findViewById(R.id.edit_note);
        btSell=(Button)findViewById(R.id.button_sell);
        btCancel=(Button)findViewById(R.id.button_cancel);
        card= (Card) getIntent().getSerializableExtra("IDCARD");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            Log.e("SENT SMS","Complite");
            new SellCardTask().execute(LinkUtils.URL_SELL_CARD);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvId.setText("ID: " + card.getId());
        tvCreateAt.setText("Created at: " + card.getCreated_at());
        tvCode.setText("Code: " + card.getCode());
        tvSeri.setText("Seri: " + card.getSeri());
        tvProduct.setText("Product: "+card.getProduct());
        tvPrice.setText("Price: "+card.getPrice()+"K");

        btSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSent= new Intent(Intent.ACTION_VIEW);
                intentSent.putExtra("sms_body", card.getCode() + "_" + card.getSeri());
                intentSent.setType("vnd.android-dir/mms-sms");
                startActivityForResult(intentSent,1);
            }
        });


    }
    private class SellCardTask extends AsyncTask<String,Void,JSONObject>{
        JSONParser parser= new JSONParser();

        @Override
        protected JSONObject doInBackground(String... strings) {
            List<NameValuePair> params= new ArrayList<>();
            params.add(new BasicNameValuePair("id",card.getId()+""));
            params.add(new BasicNameValuePair("issold","0"));
            params.add(new BasicNameValuePair("note",etNote.getText().toString()));

            return parser.makeHttpRequest(strings[0],"POST",params);
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                Log.e("JSON SENT SMS:",jsonObject.toString());
                if(jsonObject.getString("success").equals("1")){
                    Log.e("SENT SMS:",jsonObject.getString("message"));
                    Toast.makeText(getBaseContext(),"Sell Complited",Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
