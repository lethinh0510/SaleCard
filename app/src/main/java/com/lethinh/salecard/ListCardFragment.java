package com.lethinh.salecard;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lethinh.adapters.ListCardAdapter;
import com.lethinh.utils.Card;
import com.lethinh.utils.JSONParser;
import com.lethinh.utils.LinkUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thinh on 18/07/2015.
 */
public class ListCardFragment extends Fragment {
    ListView lvCards;
    ArrayList<Card> cards;
    ListCardAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cards,container,false);
        lvCards=(ListView)view.findViewById(R.id.list_cards);
        Log.e("Fragment:","onCreatView");
        return  view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Fragment:", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Fragment:","onStart");
        cards= new ArrayList<Card>();
        final Bundle bundle= getArguments();
        String product=bundle.getString("PRODUCT").toLowerCase();
        Log.e("PRODUCT", product);
        new GetCardsTask().execute(LinkUtils.URL_GET_BY_PRODUCT, product);
        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent intent=  new Intent(getActivity(),DetailCardActivity.class);
                intent.putExtra("IDCARD",  cards.get(i));
                startActivity(intent);
            }
        });


    }
    private class GetCardsTask extends AsyncTask<String,Void,JSONObject>{
        JSONParser parser = new JSONParser();
        JSONArray listcard;
        @Override
        protected JSONObject doInBackground(String... strings) {
            List<NameValuePair> params= new ArrayList<>();
            params.add(new BasicNameValuePair("product", strings[1]));
            JSONObject jsonObject=parser.makeHttpRequest(strings[0], "GET", params);
            Log.e("JSON: ",jsonObject.toString());
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                if(jsonObject.getInt("success")==1){
                    listcard=jsonObject.getJSONArray("card");
                    for(int i=0;i<listcard.length();i++){
                        JSONObject card= listcard.getJSONObject(i);
                        cards.add(new Card(card.getInt("id"),card.getString("product"),card.getString("code"),
                                card.getString("seri"),card.getInt("price"),card.getString("created_at"),
                                card.getInt("issold"),card.getString("note")));
                    }
                    adapter= new ListCardAdapter(getActivity(),cards);
                    lvCards.setAdapter(adapter);
                }
                Log.e("Card number:",cards.size()+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
