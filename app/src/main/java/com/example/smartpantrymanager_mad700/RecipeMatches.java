package com.example.smartpantrymanager_mad700;

import java.util.List;
import java.util.Locale;
final class RecipeMatches {
    private RecipeMatches() {}
    static String normalize(String raw) {
        String s = raw.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");

        //Added aliases so as to make it easier to associate matches with ingredients
        if (s.equals("tomatoes"))
            return "tomato";
        if (s.equals("potatoes"))
            return "potato";
        if (s.equals("eggs"))
            return "egg";
        if (s.equals("bananas"))
            return "banana";
        if (s.equals("apples"))
            return "apple";
        if (s.equals("onions"))
            return "onion";
        if (s.equals("carrots"))
            return "carrot";
        if (s.equals("olive oil"))
            return "oil";

        return s;

    }
    static String unit(String raw) {
        String u = raw.trim().toLowerCase(Locale.ROOT);
        if (u.equals("grams") || u.equals("gram"))
            return "g";
        if (u.equals("kilograms") || u.equals("kilogram"))
            return "kg";
        if (u.equals("milliliters") || u.equals("millilitres"))
            return "ml";
        if (u.equals("liters") || u.equals("litres"))
            return "l";
        if (u.equals("pieces") || u.equals("piece")
                || u.equals("pcs") || u.equals("x"))
            return "count";
        if (u.equals("tablespoons"))
            return "tablespoon";
        if (u.equals("teaspoons"))
            return "teaspoon";
        if (u.equals("cups"))
            return "cup";
        if (u.equals("slices"))
            return "slice";
        return u;
    }

    static String dimension(String raw) {
        String u=unit(raw);
        if(u.equals("g")||u.equals("kg"))
            return "mass";
        if(u.equals("ml")||u.equals("l"))
            return "volume";
        return u;
    }
    static double base(double qty,String raw) {
        String u=unit(raw);
        return (u.equals("kg")||u.equals("l"))?qty*1000:qty;
    }
    static boolean qualifies(Recipe recipe,List<PantryItem> pantry) {
        if(recipe.requirements.isEmpty())
            return false;
        for(Requirement need:recipe.requirements) {
            double available=0;
            for(PantryItem item:pantry)
                if(normalize(item.name).equals(normalize(need.name)) && dimension(item.unit).equals(dimension(need.unit)))
                    available+=base(item.quantity,item.unit);
            if(available+0.000001<base(need.quantity,need.unit)) return false;
        }
        return true;
    }
}