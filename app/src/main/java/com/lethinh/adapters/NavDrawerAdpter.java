package com.lethinh.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lethinh.salecard.R;
import com.lethinh.utils.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by Thinh on 18/07/2015.
 */
public class NavDrawerAdpter extends BaseAdapter {
    ArrayList<NavDrawerItem> NavDrawerItems;
    Context context;
    public NavDrawerAdpter(Context context, ArrayList<NavDrawerItem> items){
        this.context=context;
        this.NavDrawerItems=items;
    }
    @Override
    public int getCount() {
        return NavDrawerItems.size();
    }

    @Override
    public Object getItem(int i) {
        return NavDrawerItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater Iinflater=(LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view=Iinflater.inflate(R.layout.drawer_list_item,null);

        }
        ImageView icon=(ImageView)view.findViewById(R.id.icon_item_drawer);
        TextView title=(TextView)view.findViewById(R.id.title_drawer);
        TextView count=(TextView)view.findViewById(R.id.count_drawer);

        icon.setImageResource(NavDrawerItems.get(i).getIcon());
        title.setText(NavDrawerItems.get(i).getTitle());
        count.setText(NavDrawerItems.get(i).getCount()+"");



        return view;
    }
}
