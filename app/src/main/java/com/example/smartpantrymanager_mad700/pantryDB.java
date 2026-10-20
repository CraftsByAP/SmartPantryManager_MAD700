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
    public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion){ //Migrations will be added whenever the db schema will change
        private static void ass(SQLiteDatabase db, String name , String steps, Objects...ingredients ){
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
        private static void seed (SQLiteDatabase db){
            add(db ,"Fried Egg","Heat pan on medium heat.Add salt and pepper and olive oil and fry until desired", "egg" , 1 , "number", "olive oil",10, "ml");
            add(db,"Boiled Egg","Put water in a pot and bring to a boil. Take 2 eggs and place into boiling water and let sit for 15 minutes. Drain water and add cold water to cool eggs and serve","egg",2,"number","water",1,"L");
            add(db,"Scrambled eggs","Beat eggs with milk. Melt butter in a pan and stir eggs until set.","egg",2,"count","milk",30,"ml","butter",10,"g");
            add(db , "Steak", "Heat pan on high heat. take steak and season with salt and place in pan for 4 minutes on each side until desired. add butter and garlic and rosemary","Beef Rump" , 1 , "300g" , "butter",10,"g" ,"garlic clove" , 1 , "number" , "rosemary" , 1,"number");
            add(db,"Mashed Potatoes" , "Peel and dice potatoes and put in water to boil. Add salt and boil for 15 minutes. Drain water and add butter and milk and mash until desired texture","potato", 3 ,"number","water",1,"L", "butter" , 20,"g" , "salt" , 1 , "teaspoon", "milk" , 80 , "ml");
            add(db, "Fried chips" , "Peel and slice potatoes into shape of fries. Heat oil to frying temperature and fry chips until cooked. remove from oil and place on paper towl to drain oil from them. season immediately" , "potato",3,"number", "oil" , 1 , "L","Chip Spice",1 ,"packet");
            add(db, "Potato salad", "Peel , Dice , and boil potatoes for 15 mins until soft. Roast potatoes for 10 minutes at 200 degrees in air fryer. Remove and cool, then add mayonnaise, parsley , salt and pepper and mix and serve" , "potato" , 4 ,"number" , "water", 2 ,"L","mayonnaise",6,"teaspoons","parsley" , 2 ,"tablespoons" , "salt" , 1 ,"teaspoon" , "pepper" , 1,"teaspoons");
            add(db,"Beef Stew" , "Dice onions , peppers , carrots and fry until translucent. Add salt , pepper , paprika and corriander. Add diced beef and fry until golden brown. Add tomato paste and stir, then add boiling water and 1 stock cube and let sit for 2 hours.keep stirring occasionally and serve once beef is soft", "beef stewing meat" , 1, "kg or tray" ,"onion" , 1 ,"number" , "pepper", 2, "whole", "carrots", 2 ,"whole","salt",1, "or desired amount","pepper", 1,"or dessired amount", "paprika" , 1, "or desired amount" , "corriander", 1 , "or desired amount" , "tomato paste" , 1 ,"sachet" , "water", 1, "or desired amount" , "stock cube" , 1, "cube"  );
            add(db , "Pap" , "Boil water in pot and add salt. Take maize meal in a container and add water to simulate how thick you want your pap. stir and add to pot. Continue stirring until wanted consistency is reached. Close pot and stir every 10 minutes. serve hot", "maize meal", 2 , "cups" , "water" , 2 ,"L" , "salt" , 1, "or desired amount");
            add(db, "Porridge" , "Boil water in pot Take maize meal in a container and add water to simulate how thick you want your porridge. for porridge its best to be wet. stir and add to pot. Continue stirring until wanted consistency is reached. Close pot and stir every 10 minutes. serve hot", "maize meal" , 2 , "water" , 3 ,"L", "salt" , 1, "a pinch");
            add(db, "Toast","Toast until golden brown in toaster and spread butter while hot","Bread",2,"slices","butter",10,"g");
            add(db,"Toasted Cheese Sandwhich", "Grate cheese and spread butter on both sides of  bread. On a hot pan, place the sandwich and heat for 5 mins on each side. ", "bread",2,"slices","cheese",50, "g" , "butter",20,"g");
            add(db,"Toasted Cheese and Tomato Sandwich","Grate cheese and spread butter on both sides of bread.Slice 2 thin slices of tomato and place sandwich into a hot pan and heat for 7 mins on each side until cheese has melted inside.","bread",2,"slices","tomato",2,"slices","butter",20,"g");
            add(db, "Oats with Banna Cinnamon","Take 3 spoons of oats and put to boil for 10 mins or until soft. Add cinnamon to pot and stur.Slice 1 banana and serve","oats",3,"tablespoons", "cinnamon",1,"teaspoon" , "bannana",1,"count");
            add(db,"Mac&Cheese","Take 1 packet of macaroni pasta and boil with teaspoon of oil and salt to taste until soft. For cheese sauce, take 3 tablespoons of flour,with 1 cup of milk , stir until there are no lumps and season with salt,pepper and mixed herbs.Too a medium low pan, cook to a simmer and add grated cheese and stir til mixed. Add the sauce into drained pasta and mix. Put into baking dish and add grated cheese on top. bake for 30 minutes until cheese on top is melted. serve and enjoy", "Macaroni Pasta", 1,"packet", "cheese",1,"block","salt", 30,"g", "pepper",30,"g", "mixed herbs",30,"g","water", 1,"L","Milk",1,"Cup");
            add(db,"Bacon Carbonara Pasta","Take 1 packet of macaroni pasta and boil with teaspoon of oil and salt to taste until soft and drain. In a separate pan, fry diced bacon until crisp. Mix Carbonara sauce sachet with boiling water and add into pasta, add bacon and stir. serve and enjoy", "Pasta",1,"packet","diced bacon", 1, "packet", "carbonara sauce",1,"sachet", "water",1,"L");
            add(db,"Coleslaw","Grate cabbage and carrot and place into one bowl. Add mayonnaise and stir until mixed. serve cold","Cabbage",0.25,"quarter","Carrot",2,"number","mayonnaise",3,"tablespoons");
            add(db,"Boiled rice","Rinse rice and simmer in water until soft.add salt to taste","rice",1,"cup","water",3,"cup","salt",1,"teaspoon");
            add(db,"Savoury rice with peas","Rinse rice and simmer in water until soft.add salt to taste and tumeric to colour.5 min before rice is cooked,add frozen peas and cook until ready. serve and enjoy","rice",1,"cup","water",3,"cup","salt",1,"teaspoon","frozen peas",0.5,"cup","tumeric",2,"teaspoons");
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







        }

    }
}
