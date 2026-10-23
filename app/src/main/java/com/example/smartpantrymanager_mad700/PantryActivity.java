package com.example.smartpantrymanager_mad700;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.*;

public final class PantryActivity extends Activity {
    private pantryDB db;
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        db=new pantryDB(this);
    }
    @Override
    //query after adding and editing ingredients
    public void onResume(){
        super.onResume();
        show();
    }
    private void show(){
        LinearLayout root=Ui.column(this);
        setContentView(root);
        root.addView(Ui.title(this,"MY PANTRY"),Ui.space(this));
        root.addView(Ui.button(this,"+ ADD INGREDIENTS",()->startActivity(new Intent(this,editIngredients.class))),Ui.space(this));
        ArrayList<PantryItem> items=db.pantry(getPreferences("expiry_first"));
        if(items.isEmpty())
            root.addView(Ui.text(this,"The Pantry is Empty.Add ingredients to continue",16));
        ListView list=new ListView(this);
        list.setAdapter(new PantryAdapter(this,items));
        list.setOnItemClickListener((parent,view,pos,id)->{
            Intent intent=new Intent(this,editIngredients.class);
            intent.putExtra("item_id",items.get(pos).id);
            startActivity(intent);
        });
        root.addView(list,new LinearLayout.LayoutParams(-1,0,1));
        Ui.nav(this,root);
    }
    private boolean getPreferences(String key){return getSharedPreferences("pantry_settings",MODE_PRIVATE).getBoolean(key,false);
    }
}

