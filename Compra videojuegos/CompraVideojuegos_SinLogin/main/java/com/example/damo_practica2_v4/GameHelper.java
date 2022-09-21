package com.example.damo_practica2_v4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GameHelper extends SQLiteOpenHelper {
    public GameHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GAMES ("
                + "GAME_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "GAME_NAME TEXT, "
                + "GAME_COMPANY TEXT, "
                + "GAME_TYPE TEXT, "
                + "IMAGE_ID INTEGER, "
                + "GAME_PRICE REAL, "
                + "BUYED BOOLEAN, "
                + "CART BOOLEAN, "
                + "FAVORITE BOOLEAN); ");
        addVideogame (db, "Gran Turismo","Sony", "PS4", R.drawable.gt7, 70, false, false, false);
        addVideogame (db, "Residen Evil","Campcpm", "PS4", R.drawable.resident, 40, false, false, false);
        addVideogame (db, "Forza","Playground Games", "XBOX", R.drawable.forza, 70, false, false, false);
        addVideogame (db, "Halo","Bungie Studios", "XBOX", R.drawable.halo, 60, false, false, false);
        addVideogame (db, "Killer Instict","Double Helix", "XBOX", R.drawable.killer, 15, true, false, false);
        addVideogame (db, "Scrom","Ebb Software", "XBOX", R.drawable.scrom, 20, false, false, false);
        addVideogame (db, "Horizon","Guerrilla Games", "PC", R.drawable.horizon, 20, false, false, false);
        addVideogame (db, "BloodBorme","From Software", "PC", R.drawable.blood, 30, false, false, false);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public static void addVideogame (SQLiteDatabase db, String name, String company, String type, int imageID, float price, boolean buyed, boolean cart, boolean favorite)
    {
        ContentValues gamesData = new ContentValues();
        gamesData.put("GAME_NAME", name);
        gamesData.put("GAME_COMPANY", company);
        gamesData.put("GAME_TYPE", type);
        gamesData.put("IMAGE_ID", imageID);
        gamesData.put("GAME_PRICE", price);
        gamesData.put("BUYED", buyed);
        gamesData.put("CART", cart);
        gamesData.put("FAVORITE", favorite);
        db.insert("GAMES", null, gamesData);
    }

}
