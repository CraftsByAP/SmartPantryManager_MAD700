package com.example.smartpantrymanager_mad700;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import java.util.*;
public final class suggestionsRecipe extends Activity{
    private PantryDb db;
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        db=new PantryDb(this);
    }
    @Override
    public void onResume(){
        super.onResume();
        show();
    }
    private void show(){
        LinearLayout root=Ui.column(this);
        setContentView(root);
        root.addView(Ui.title(this,"Suggested recipes"),Ui.space(this));
        root.addView(Ui.text(this,"Every ingredient and its full quantity must be in your pantry.",15),Ui.space(this));
        ArrayList<PantryItem> pantry=db.pantry(false);
        ArrayList<Recipe> matches=new ArrayList<>();
        for(Recipe recipe:db.recipes())
            if(Matching.qualifies(recipe,pantry))
                matches.add(recipe);
        if(matches.isEmpty())
            root.addView(Ui.text(this,"No Recipe match.Check quantity and add ingredients",16),Ui.space(this));
        ArrayList<String> labels=new ArrayList<>();
        for(Recipe r:matches)
            labels.add(r.name);
        ListView list=new ListView(this);
        list.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,labels));
        list.setOnItemClickListener((parent,view,position,id)->{
            Intent intent=new Intent(this,RecipeDetailActivity.class);
            intent.putExtra("recipe_id",matches.get(position).id);
            startActivity(intent);
        });
        root.addView(list,new LinearLayout.LayoutParams(-1,0,1));
        Ui.nav(this,root);
    }
}

