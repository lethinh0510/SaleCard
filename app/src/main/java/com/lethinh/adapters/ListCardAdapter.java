package com.lethinh.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lethinh.salecard.R;
import com.lethinh.utils.Card;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Thinh on 18/07/2015.
 */
public class ListCardAdapter extends BaseAdapter {
    Context context;
    ArrayList<Card> cards;

    public ListCardAdapter(Context context, ArrayList<Card> listcard) {
        this.context = context;
        this.cards = listcard;
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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_card, null);
        }
        TextView tvId = (TextView) view.findViewById(R.id.text_id);
        TextView tvProduct = (TextView) view.findViewById(R.id.text_product);


        TextView tvPrice = (TextView) view.findViewById(R.id.text_price);
        TextView tvCreateAt = (TextView) view.findViewById(R.id.text_created_at);
        //TextView tvIsSold = (TextView) view.findViewById(R.id.text_issold);
        //TextView tvNote = (TextView) view.findViewById(R.id.text_note);

        tvId.setText(cards.get(i).getId() + "");
        tvProduct.setText(cards.get(i).getProduct());
        tvPrice.setText(cards.get(i).getPrice() + "");
        String date = cards.get(i).getCreated_at();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        CharSequence created_at = DateUtils.getRelativeTimeSpanString(date1.getTime());
        tvCreateAt.setText(created_at);
       // tvIsSold.setText(cards.get(i).getIssold() + "");
        //tvNote.setText(cards.get(i).getNote());

        return view;
    }
}
