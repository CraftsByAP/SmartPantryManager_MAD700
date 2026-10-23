package com.example.smartpantrymanager_mad700;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public final class pantrySettings extends Activity {
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        LinearLayout root=Ui.column(this);
        setContentView(root);
        root.addView(Ui.title(this,"Settings"),Ui.space(this));
        root.addView(Ui.text(this,"Pantry List",18),Ui.space(this));
        Switch sort=new Switch(this);
        sort.setText("Show: Earliest Expiry Date");
        sort.setChecked(getSharedPreferences("pantry_settings",MODE_PRIVATE).getBoolean("expiry_first",false));
        sort.setOnCheckedChangeListener((CompoundButton button,boolean checked)->getSharedPreferences("pantry_settings",MODE_PRIVATE).edit().putBoolean("expiry_first",checked).apply());
        root.addView(sort,Ui.space(this));
        root.addView(new android.widget.Space(this),new LinearLayout.LayoutParams(-1,0,1));
        Ui.nav(this,root);
    }
}