package com.example.smartpantrymanager_mad700;

import android.app.Activity;
import android.view.*;
import android.widget.*;
import java.util.*;
final class PantryAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<PantryItem> items;
    PantryAdapter(Activity a,List<PantryItem> data){
        activity=a;
        items=data;
    }
    public int getCount(){
        return items.size();
    }
    public PantryItem getItem(int position){
        return items.get(position);
    }
    public long getItemId(int position){
        return getItem(position).id;
    }
    public View getView(int position,View recycled,ViewGroup parent){
        LinearLayout row=new LinearLayout(activity);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(Ui.dp(activity,12),Ui.dp(activity,10),Ui.dp(activity,12),Ui.dp(activity,10));
        PantryItem item=getItem(position);
        TextView name=Ui.text(activity,item.name,19);
        row.addView(name);
        String subtitle=Ui.amount(item.quantity) +" "+item.unit+(item.expiry==null?"":"Expires "+item.expiry);
        row.addView(Ui.text(activity,subtitle,14));
        return row;
    }
}