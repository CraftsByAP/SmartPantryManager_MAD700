# SmartPantryManager_MAD700
 A Final Year Assignment in Mobile App Development. This assignment has tasked me to create a smart pantry manager. It tracks pantry ingredients and suggests only recipes whose entire ingredient list is present when there is enough ingredients.It stores the pantry and 18 seeded recipes on SQLite using `SQLiteOpenHelper`. I chose SQLite because it is relational and the data is local.
 
 ##Run in : Android Studio
- Use Android Studio with JDK 17 and Android SDK Platform 35 installed.
- Open folder as a project. Allow Gradle to sync (Android Gradle Plugin 8.7.3). If Studio asks for a Gradle distribution, use its installed Gradle 8.9 or add a Gradle wrapper using `gradle wrapper --gradle-version 8.9` on a machine with Gradle installed.
- Create an emulator with Android 6.0 (API 23) or newer, or attach an Android device with USB debugging.
- Run the `app` configuration. The first launch inserts 18 recipes. Pantry data persists between launches.
- Add ingredients from My pantry, tap an item to edit/delete, use Recipes to check suggestions, tap a recipe to see its ingredients and method, and change expiry sorting in Settings.

##Conventions 
- Units: 
    -'count'/'x' (whole pieces)
    - 'g'/'kg' and 'ml'/'l'
    - 'packet'/'sachet' 
    -'tablespoon'/'teaspoon'

##References 
MAD700 Study Guide : 
Activities , Layouts & Intents (Chapter 3.2) 
SharedPreferences and SQLite/SQLiteOpenHelper (Chapter 3 section 3.5)
ListView, adapters and 'onResume()'(CHapter 5.2)
NO MAPS /LOCATION APIs are used in this project 



