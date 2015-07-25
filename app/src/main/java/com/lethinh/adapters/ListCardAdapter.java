package com.lethinh.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lethinh.salecard.R;
import com.lethinh.utils.Card;

import java.util.ArrayList;

/**
 * Created by Thinh on 18/07/2015.
 */
public class ListCardAdapter extends BaseAdapter {
    Context context;
    ArrayList<Card> cards;
    public ListCardAdapter(Context context, ArrayList<Card> listcard){
        this.context=context;
        this.cards=listcard;
    }
    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int i) {
        return cards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.list_item_card,null);
        }
        TextView tvId=(TextView)view.findViewById(R.id.text_id);
        TextView tvProduct=(TextView)view.findViewById(R.id.text_product);


        TextView tvPrice=(TextView)view.findViewById(R.id.text_price);
        TextView tvCreateAt=(TextView)view.findViewById(R.id.text_created_at);
        TextView tvIsSold=(TextView)view.findViewById(R.id.text_issold);
        TextView tvNote=(TextView)view.findViewById(R.id.text_note);

        tvId.setText(cards.get(i).getId()+"");
        tvProduct.setText(cards.get(i).getProduct());

        tvPrice.setText(cards.get(i).getPrice()+"");
        tvCreateAt.setText(cards.get(i).getCreated_at());
        tvIsSold.setText(cards.get(i).getIssold()+"");
        tvNote.setText(cards.get(i).getNote());

        return view;
    }
}
