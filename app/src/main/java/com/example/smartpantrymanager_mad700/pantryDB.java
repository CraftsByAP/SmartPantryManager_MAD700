package com.example.smartpantrymanager_mad700;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.*;
import java.util.*;


//MAD700 Study Guide : Topic 3 tackles database patterns and the use of SQLiteOpenHelper
final class pantryDB extends SQLiteOpenHelper{
    pantryDB(Context context){

        super(context, "pantry.db" , null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE pantry " +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL , " +
                "quantity REAL NOT NULL CHECK (quantity>0), " +
                "unit TEXT NOT NULL, expiry TEXT) ");
        db.execSQL("CREATE TABLE recipes " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "instructions TEXT NOT NULL)");
        db.execSQL("CREATE TABLE ingredients (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "recipe_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "quantity REAL NOT NULL ," +
                "unit TEXT NOT NULL , " +
                "FOREIGN KEY (recipe_id) REFERENCES recipes(_id))");
        seed(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }//Migrations will be added whenever the db schema will change
        private static void add (SQLiteDatabase db, String name , String steps, Objects...ingredients ){
            ContentValues recipe = new ContentValues();
            recipe.put("name", name );
            recipe.put("steps", steps);
            long recipeId = db.insertOrThrow("recipes", null , recipe);
            for (int i = 0; i<ingredients.length; i+= 3){
                ContentValues values = new ContentValues();
                values.put("recipe_id", recipeId);
                values.put("name", (String) ingredients[i]);
                values.put("quantity", ((Number)ingredients[i + 1]).doubleValue());
                values.put("unit",(String)ingredients[i+2]);
                db.insertOrThrow("requirements", null, values);
            }
        }
        //Recipe lists with all ingredients
        private static void seed (SQLiteDatabase db) {
            add(db, "Fried Egg", "Heat pan on medium heat.Add salt and pepper and olive oil and fry until desired", "Egg", 1, "x", "Olive oil", 10, "ml");
            add(db, "Boiled Egg", "Put water in a pot and bring to a boil. Take 2 eggs and place into boiling water and let sit for 15 minutes. Drain water and add cold water to cool eggs and serve", "Egg", 2, "x", "Water", 1, "L");
            add(db, "Scrambled eggs", "Beat eggs with milk. Melt butter in a pan and stir eggs until set.", "Egg", 2, "x", "Milk", 30, "ml", "Butter", 10, "g");
            add(db, "Steak", "Heat pan on high heat. take steak and season with salt and place in pan for 4 minutes on each side until desired. add butter and garlic and rosemary", "Beef Rump 300g", 1, "x", "Butter", 10, "g", "Garlic clove", 1, "x", "Rosemary", 1, "x");
            add(db, "Mashed Potatoes", "Peel and dice potatoes and put in water to boil. Add salt and boil for 15 minutes. Drain water and add butter and milk and mash until desired texture", "Potato", 3, "x", "Water", 1, "L", "Butter", 20, "g", "Salt", 1, "teaspoon", "Milk", 80, "ml");
            add(db, "Fried chips", "Peel and slice potatoes into shape of fries. Heat oil to frying temperature and fry chips until cooked. remove from oil and place on paper towl to drain oil from them. season immediately", "Potato", 3, "x", "Olive oil", 1, "L", "Chip Spice", 1, "packet");
            add(db, "Potato salad", "Peel , Dice , and boil potatoes for 15 mins until soft. Roast potatoes for 10 minutes at 200 degrees in air fryer. Remove and cool, then add mayonnaise, parsley , salt and pepper and mix and serve", "Potato", 4, "x", "Water", 2, "L", "Mayonnaise", 6, "teaspoon", "Parsley", 2, "tablespoon", "Salt", 1, "teaspoon", "Pepper", 1, "teaspoon");
            add(db, "Beef Stew", "Dice onions , peppers , carrots and fry until translucent. Add salt , pepper , paprika and corriander. Add diced beef and fry until golden brown. Add tomato paste and stir, then add boiling water and 1 stock cube and let sit for 2 hours.keep stirring occasionally and serve once beef is soft", "Beef stewing meat", 1, "kg", "Onion", 1, "x", "Green Pepper", 2, "x", "Carrots", 2, "x", "Salt", 1, "teaspoon", "Pepper", 1, "teaspoon", "Paprika", 1, "teaspoon", "Corriander", 1, "teaspoon", "Tomato paste", 1, "sachet", "Water", 1, "L", "Stock cube", 1, "x");
            add(db, "Pap", "Boil water in pot and add salt. Take maize meal in a container and add water to simulate how thick you want your pap. stir and add to pot. Continue stirring until wanted consistency is reached. Close pot and stir every 10 minutes. serve hot", "Maize meal", 2, "cup", "Water", 2, "L", "Salt", 1, "teaspoon");
            add(db, "Porridge", "Boil water in pot Take maize meal in a container and add water to simulate how thick you want your porridge. for porridge its best to be wet. stir and add to pot. Continue stirring until wanted consistency is reached. Close pot and stir every 10 minutes. serve hot", "Maize meal", 2,"cup", "Water", 3, "L", "Salt", 1, "teaspoon");
            add(db, "Toast", "Toast until golden brown in toaster and spread butter while hot", "Bread", 2, "x", "Butter", 10, "g");
            add(db, "Toasted Cheese Sandwhich", "Grate cheese and spread butter on both sides of  bread. On a hot pan, place the sandwich and heat for 5 mins on each side. ", "Bread", 2, "x", "Cheese", 50, "g", "Butter", 20, "g");
            add(db, "Toasted Cheese and Tomato Sandwich", "Grate cheese and spread butter on both sides of bread.Slice 2 thin slices of tomato and place sandwich into a hot pan and heat for 7 mins on each side until cheese has melted inside.", "Bread", 2, "slices", "Tomato", 2, "slices", "Butter", 20, "g");
            add(db, "Oats with Banna Cinnamon", "Take 3 spoons of oats and put to boil for 10 mins or until soft. Add cinnamon to pot and stur.Slice 1 banana and serve", "Oats", 3, "tablespoons", "Cinnamon", 1, "teaspoon", "Bannana", 1, "x");
            add(db, "Mac&Cheese", "Take 1 packet of macaroni pasta and boil with teaspoon of oil and salt to taste until soft. For cheese sauce, take 3 tablespoons of flour,with 1 cup of milk , stir until there are no lumps and season with salt,pepper and mixed herbs.Too a medium low pan, cook to a simmer and add grated cheese and stir til mixed. Add the sauce into drained pasta and mix. Put into baking dish and add grated cheese on top. bake for 30 minutes until cheese on top is melted. serve and enjoy", "Macaroni Pasta", 1, "packet", "Cheese", 1, "x", "Salt", 30, "g", "Pepper", 30, "g", "Mixed herbs", 30, "g", "Water", 1, "L", "Milk", 1, "cup");
            add(db, "Bacon Carbonara Pasta", "Take 1 packet of macaroni pasta and boil with teaspoon of oil and salt to taste until soft and drain. In a separate pan, fry diced bacon until crisp. Mix Carbonara sauce sachet with boiling water and add into pasta, add bacon and stir. serve and enjoy", "Pasta", 1, "packet", "Diced bacon", 1, "packet", "Carbonara sauce", 1, "sachet", "Water", 1, "L");
            add(db, "Coleslaw", "Grate cabbage and carrot and place into one bowl. Add mayonnaise and stir until mixed. serve cold", "Quarter Cabbage", 1, "x", "Carrot", 2, "x", "Mayonnaise", 3, "tablespoon");
            add(db, "Boiled rice", "Rinse rice and simmer in water until soft.add salt to taste", "Rice", 1, "cup", "Water", 3, "cup", "Salt", 1, "teaspoon");
            add(db, "Savoury rice with peas", "Rinse rice and simmer in water until soft.add salt to taste and tumeric to colour.5 min before rice is cooked,add frozen peas and cook until ready. serve and enjoy", "Rice", 1, "cup", "Water", 3, "cup", "Salt", 1, "teaspoon", "Frozen peas", 0.5, "cup", "Tumeric", 2, "teaspoon");
        }
            //Save items into db
            long saveItem(long id, String name, double quantity , String unit ,String expiry ){
                ContentValues v  =new
                        COntentValues();
                v.put("name",Matching.normalize(name));
                v.put("quantity",quantity);
                v.put("unit",Matching.unit(unit));
                v.put("expiry",expiry.isEmpty()?null:expiry);
                SQLiteDatabase db = getWritableDatabase();
                if(id<0)
                    return db.insertOrThrow("pantry",null,v);
                return db.update("pantry",v,"_id=?", new String[] {String.valueOf(id)})==1?id:-1;
            }
            //Delete item from pantry and db
            void deleteItem(long id){
                getWritableDatabase().delete("pantry", "_id=?", new String[]{String.valueOf(id)});
            }
            PantryItem item(long id){
                try(Cursor c = getReadableDatabase().query("pantry",null,"_id=?",new String[]{String.valueOf(id)},null,null,null)) {
                    return c.moveToFirst()?readItem(c):null;
                }

            }
            private PantryItem readItem(Cursor c) {
                return new PantryItem(c.getLong(c.getColumnIndexOrThrow("_id")),c.getString(c.getColumnIndexOrThrow("name")),c.getDouble(c.getColumnIndexOrThrow("quantity")),c.getString(c.getColumnIndexOrThrow("unit")),c.getString(c.getColumnIndexOrThrow("expiry")));
            }
            ArrayList<PantryItem> pantry(boolean expiryFirst) {
                ArrayList<PantryItem> items=new ArrayList<>();
                String order=expiryFirst?"CASE WHEN expiry IS NULL THEN 1 ELSE 0 END, expiry ASC, name COLLATE NOCASE":"name COLLATE NOCASE";
                try(Cursor c=getReadableDatabase().query("pantry",null,null,null,null,null,order)) {
                    while(c.moveToNext())items.add(readItem(c));
                }return items;
            }
            ArrayList<Recipe> recipes() {
                LinkedHashMap<Long,Recipe> all=new LinkedHashMap<>();
                SQLiteDatabase db=getReadableDatabase();
                try(Cursor c=db.query("recipes",null,null,null,null,null,"name COLLATE NOCASE")) {
                    while(c.moveToNext()) {
                        long id=c.getLong(c.getColumnIndexOrThrow("_id"));
                        all.put(id,new Recipe(id,c.getString(c.getColumnIndexOrThrow("name")),c.getString(c.getColumnIndexOrThrow("steps"))));
                    }
                }
                try(Cursor c=db.query("requirements",null,null,null,null,null,"_id")) {
                    while(c.moveToNext()) {
                        Recipe recipe=all.get(c.getLong(c.getColumnIndexOrThrow("recipe_id")));
                        if(recipe!=null)
                            recipe.requirements.add(new Requirement(c.getString(c.getColumnIndexOrThrow("name")),c.getDouble(c.getColumnIndexOrThrow("quantity")),c.getString(c.getColumnIndexOrThrow("unit"))));
                    }
                }return new ArrayList<>(all.values());

            }
            Recipe recipe(long id) {
                for(Recipe r:recipes())
                    if(r.id==id)
                        return r;
                return null;
            }
        }







        }

    }
}
