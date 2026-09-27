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
        db.execSQL
    }
}
