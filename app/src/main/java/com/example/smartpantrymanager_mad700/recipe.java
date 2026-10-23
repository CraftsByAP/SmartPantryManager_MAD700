package com.example.smartpantrymanager_mad700;
import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
public final class recipe extends Activity{
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        Recipe recipe = new pantryDB(this).recipe(getIntent().getLongExtra("recipe_id",-1));
        if(recipe==null){
            finish();
            return;
        }
        LinearLayout root=Ui.column(this);
        setContentView(root);
        LinearLayout content=Ui.column(this);
        Ui.scrolled(this,root,content);
        content.addView(Ui.title(this,recipe.name),Ui.space(this));
        content.addView(Ui.text(this,"Ingredients",20),Ui.space(this));
        for(Requirement r:recipe.requirements)
            content.addView(Ui.text(this,"-  "+Ui.amount(r.quantity)+" "+r.unit+" "+r.name,17));
        content.addView(Ui.text(this,"Method",20),Ui.space(this));
        content.addView(Ui.text(this,recipe.steps,17),Ui.space(this));
        root.addView(Ui.button(this,"Back To Suggestions",this::finish));
        Ui.nav(this,root);
    }
}
