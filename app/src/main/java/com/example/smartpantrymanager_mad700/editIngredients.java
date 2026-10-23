package com.example.smartpantrymanager_mad700;
import android.app.Activity;
import android.os.Bundle;
import android.text.*;
import android.widget.*;
import android.app.AlertDialog;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
//Creating the logic behind the xml text inputs
public class editIngredients extends Activity {
    private final String[] units={"x","g","kg","ml","L","packet","sachet","teaspoon","tablespoon"};
    private pantryDB db;
    private long itemId;
    private EditText name,quantity,expiry;
    private Spinner unit;
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        db=new pantryDB(this);
        itemId=getIntent().getLongExtra("item_id",-1);
        LinearLayout root=Ui.column(this);
        setContentView(root);
        root.addView(Ui.title(this,itemId<0?"Add ingredient":"Edit ingredient"),Ui.space(this));
        name=new EditText(this);
        name.setHint("Ingredient name (e.g. tomato)");
        name.setSingleLine(true);
        root.addView(name,Ui.space(this));
        quantity=new EditText(this);
        quantity.setHint("Quantity (greater than 0)");
        quantity.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        root.addView(quantity,Ui.space(this));
        unit=new Spinner(this);
        unit.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,units));
        root.addView(unit,Ui.space(this));
        expiry=new EditText(this);
        expiry.setHint("Optional expiry: YYYY-MM-DD");
        expiry.setSingleLine(true);
        root.addView(expiry,Ui.space(this));
        root.addView(Ui.button(this,"Save",this::save),Ui.space(this));
        if(itemId>=0){
            PantryItem item=db.item(itemId);
            if(item==null){
                finish();
                return;
            }
            name.setText(item.name);
            quantity.setText(Ui.amount(item.quantity));
            expiry.setText(item.expiry==null?"":item.expiry);
            for (int i = 0; i < units.length; i++) {
                if (RecipeMatches.unit(units[i]).equals(item.unit)) {
                    unit.setSelection(i);
                    break;
                }
            }
            root.addView(Ui.button(this,"Delete ingredient",()->new AlertDialog.Builder(this).setTitle("Delete "+item.name+"?").setMessage("This will delete it from the pantry").setNegativeButton("Cancel",null).setPositiveButton("Delete",(dialog,which)->{db.deleteItem(itemId);
                finish();}).show()),Ui.space(this));
        }
        root.addView(Ui.button(this,"Cancel",this::finish));
    }
    private void save(){
        String n=name.getText().toString().trim();
        if(n.isEmpty()){
            name.setError("Enter an ingredient name");
            return;
        }
        if(!n.matches("[\\p{L}][\\p{L} '-]*")){
            name.setError("Use letters and spaces for the ingredient name");
            return;
        }
        double q;
        try{
            q=Double.parseDouble(quantity.getText().toString().trim());
        }catch(NumberFormatException e){
            quantity.setError("Enter a valid quantity");
            return;
        }
        if(!Double.isFinite(q)||q<=0){
            quantity.setError("Quantity cannot be less than zero");
            return;
        }
        String date=expiry.getText().toString().trim();
        if(!date.isEmpty()){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd",Locale.ROOT);format.setLenient(false);
            try{
                if(!date.matches("\\d{4}-\\d{2}-\\d{2}"))
                    throw new ParseException(date,0);
                format.parse(date);
            }catch(ParseException e){
                expiry.setError("Use a real date in year-month-day format");
                return;
            }
        }
        db.saveItem(itemId,n,q,units[unit.getSelectedItemPosition()],date);
        finish();
    }
}
