package com.example.smartpantrymanager_mad700;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.*;
import android.widget.*;
import java.util.*;


public class MainActivity extends AppCompatActivity {
    private pantryDB db;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        db = new pantryDB(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        show;
    }

    private void show() {
        LinearLayout root = Ui.column(this);
        setContentView(root);
        root.addView(Ui.title(this, "THE PANTRY"), Ui.space(this));
        root.addView(Ui.button(this, "Add Ingredient", () -> startActivity(new Intent(this, editIngredients.class))), Ui.space(this));
        ArrayList<PantryItem> items = db.pantry(getPreferences("firstExpire"));
        if (items.isEmpty()) root.addView(Ui.text(this, "PANTRY IS EMPTY: ADD INGREDIENTS"));
        ListView list = new ListView(this);
        list.setAdapter(new pantryAdapter(this, items));
        list.setOnItemClickListener((parent, view, pos, id) -> {
            Intent intent = new Intnet(this, editIngredients.class);
            intent.putExtra("item_id", items.get(pos).id);
            startActivity(intent);
        });
        root.addView(list, new LinearLayout.LayoutParams(-1, 0, 1));
        Ui.nav(this, root);
    }

    private boolean getPreference(String key) {
        return getSharedPreferences("pantrySettings", MODE_PRIVATE).getBoolean(key, false);
    }
}

