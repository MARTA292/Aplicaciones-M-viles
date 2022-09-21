package com.example.damo_practica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] consoles_type = { "Todos", "PS4", "XBOX", "PC"};
    RecyclerView gameList;
    private ArrayList<Game> juegos = new ArrayList<Game>();

    private SQLiteDatabase database;
    private GameHelper gameHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //gameList =  this.findViewById(R.id.gameRecyclerView);
        gameHelper = new GameHelper(this, "gamesdatabase", 1) ;
        database = gameHelper.getReadableDatabase();
        Cursor cursor = database.query("GAMES",
                new String[] {"GAME_ID", "GAME_NAME", "GAME_COMPANY", "IMAGE_ID", "GAME_PRICE"},
                null,
                null,
                null, null, null);
        if(juegos.isEmpty()) {
            setGames(TipoConsola.Todos);
        }
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, consoles_type);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(arrayAdapter);

    }

    private void setGames(TipoConsola console_type){
        juegos.clear();
        gameHelper = new GameHelper(this, "gamesdatabase", 1);
        database = gameHelper.getReadableDatabase();
        String query = "SELECT * FROM GAMES";
        Log.d("Console type: ", console_type.toString());
        if (console_type == TipoConsola.PS4){
            Log.d("PS4", "He entrado en la consola PS4");
            query = "SELECT * FROM GAMES WHERE GAME_TYPE = 'PS4'";
        }else if (console_type == TipoConsola.XBOX){
            query = "SELECT * FROM GAMES WHERE GAME_TYPE = 'XBOX'";
        }else if (console_type == TipoConsola.PC){
            query = "SELECT * FROM GAMES WHERE GAME_TYPE = 'PC'";
        }
        Log.d("La query es ", query);
        cursor = database.rawQuery(query, null);
        //Copiamos todos los datos de la tabla
        if (cursor.getCount() > 0) {
            // Situo en la primera posición
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex("GAME_ID");
            int nameIndex = cursor.getColumnIndex("GAME_NAME");
            int companyIndex = cursor.getColumnIndex("GAME_COMPANY");
            int consoleIndex = cursor.getColumnIndex("GAME_TYPE");
            int imageIndex = cursor.getColumnIndex("IMAGE_ID");
            int priceIndex = cursor.getColumnIndex("GAME_PRICE");
            int buyedIndex = cursor.getColumnIndex("BUYED");
            int cartIndex = cursor.getColumnIndex("CART");

            String name, company, type;
            int id, image, buyedInt, cartInt;
            float price;
            boolean buyed, cart;
            do {
                id = cursor.getInt(idIndex);
                name = cursor.getString(nameIndex);
                company = cursor.getString(companyIndex);
                type = cursor.getString(consoleIndex);
                image = cursor.getInt(imageIndex);
                price = cursor.getFloat(priceIndex);
                buyedInt = cursor.getInt(buyedIndex);
                cartInt = cursor.getInt(cartIndex);
                if (buyedInt == 0) { buyed = false;
                } else { buyed = true; }
                if (cartInt == 0) { cart = false;
                } else { cart = true;  }
                //Añadimos el juego a la lista
                //Game.m_gameList.add(new Game(id, name, company, TipoConsola.valueOf(type), image, price, buyed, cart));
                juegos.add(new Game(id, name, company, TipoConsola.valueOf(type), image, price, buyed, cart));
            } while (cursor.moveToNext());
        }
        database.close();

        GameListAdapter adapter = new GameListAdapter(this, juegos);
        ListView artistList = findViewById(R.id.game_list_fragment);
        artistList.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(), consoles_type[position] , Toast.LENGTH_LONG).show();
        Log.d("Tipo de consola", consoles_type[position]);
        if (consoles_type[position] == "Todos"){
            //Añadir a la lista todos los juegos
            //allGames();
            setGames(TipoConsola.Todos);
        }else if (consoles_type[position] == "PS4"){
            setGames(TipoConsola.PS4);
        }else if (consoles_type[position] == "XBOX"){
            setGames(TipoConsola.XBOX);
        }else if (consoles_type[position] == "PC"){
            setGames(TipoConsola.PC);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    public void Comprar(View v){
        Log.d("Se ha comprado el juego", v.findViewById(R.id.name).toString());
    }

    public ArrayList<Game> allGames(){
        Game.m_gameList.clear();
        gameHelper = new GameHelper(this, "gamesdatabase", 1);
        database = gameHelper.getReadableDatabase();
        String query = "SELECT * FROM GAMES";
        //Copiamos todos los datos de la tabla
        cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            // Situo en la primera posición
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex("GAME_ID");
            int nameIndex = cursor.getColumnIndex("GAME_NAME");
            int companyIndex = cursor.getColumnIndex("GAME_COMPANY");
            int consoleIndex = cursor.getColumnIndex("GAME_TYPE");
            int imageIndex = cursor.getColumnIndex("IMAGE_ID");
            int priceIndex = cursor.getColumnIndex("GAME_PRICE");
            int buyedIndex = cursor.getColumnIndex("BUYED");
            int cartIndex = cursor.getColumnIndex("CART");

            String name, company, type;
            int id, image, buyedInt, cartInt;
            float price;
            boolean buyed, cart;
            do {
                id = cursor.getInt(idIndex);
                name = cursor.getString(nameIndex);
                company = cursor.getString(companyIndex);
                type = cursor.getString(consoleIndex);
                image = cursor.getInt(imageIndex);
                price = cursor.getFloat(priceIndex);
                buyedInt = cursor.getInt(buyedIndex);
                cartInt = cursor.getInt(cartIndex);
                if (buyedInt == 0) { buyed = false;
                } else { buyed = true; }
                if (cartInt == 0) { cart = false;
                } else { cart = true;  }
                //Añadimos el juego a la lista
                Game.m_gameList.add(new Game(id, name, company, TipoConsola.valueOf(type), image, price, buyed, cart));
            } while (cursor.moveToNext());
        }
        database.close();
        return Game.m_gameList;
    }

    public ArrayList<Game> ps4Games(){
        Game.m_gameList.clear();
        gameHelper = new GameHelper(this, "gamesdatabase", 1);
        database = gameHelper.getReadableDatabase();
        String query = "SELECT * FROM GAMES WHERE GAME_TYPE = 'PS4'";
        //Copiamos todos los datos de la tabla
        cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            // Situo en la primera posición
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex("GAME_ID");
            int nameIndex = cursor.getColumnIndex("GAME_NAME");
            int companyIndex = cursor.getColumnIndex("GAME_COMPANY");
            int consoleIndex = cursor.getColumnIndex("GAME_TYPE");
            int imageIndex = cursor.getColumnIndex("IMAGE_ID");
            int priceIndex = cursor.getColumnIndex("GAME_PRICE");
            int buyedIndex = cursor.getColumnIndex("BUYED");
            int cartIndex = cursor.getColumnIndex("CART");

            String name, company, type;
            int id, image, buyedInt, cartInt;
            float price;
            boolean buyed, cart;
            do {
                id = cursor.getInt(idIndex);
                name = cursor.getString(nameIndex);
                company = cursor.getString(companyIndex);
                type = cursor.getString(consoleIndex);
                image = cursor.getInt(imageIndex);
                price = cursor.getFloat(priceIndex);
                buyedInt = cursor.getInt(buyedIndex);
                cartInt = cursor.getInt(cartIndex);
                if (buyedInt == 0) { buyed = false;
                } else { buyed = true; }
                if (cartInt == 0) { cart = false;
                } else { cart = true;  }
                //Añadimos el juego a la lista
                Game.m_gameList.add(new Game(id, name, company, TipoConsola.valueOf(type), image, price, buyed, cart));
            } while (cursor.moveToNext());
        }
        database.close();
        return Game.m_gameList;
    }

    public ArrayList<Game> xboxGames(){
        Game.m_gameList.clear();
        gameHelper = new GameHelper(this, "gamesdatabase", 1);
        database = gameHelper.getReadableDatabase();
        String query = "SELECT * FROM GAMES WHERE GAME_TYPE = 'XBOX'";
        //Copiamos todos los datos de la tabla
        cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            // Situo en la primera posición
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex("GAME_ID");
            int nameIndex = cursor.getColumnIndex("GAME_NAME");
            int companyIndex = cursor.getColumnIndex("GAME_COMPANY");
            int consoleIndex = cursor.getColumnIndex("GAME_TYPE");
            int imageIndex = cursor.getColumnIndex("IMAGE_ID");
            int priceIndex = cursor.getColumnIndex("GAME_PRICE");
            int buyedIndex = cursor.getColumnIndex("BUYED");
            int cartIndex = cursor.getColumnIndex("CART");

            String name, company, type;
            int id, image, buyedInt, cartInt;
            float price;
            boolean buyed, cart;
            do {
                id = cursor.getInt(idIndex);
                name = cursor.getString(nameIndex);
                company = cursor.getString(companyIndex);
                type = cursor.getString(consoleIndex);
                image = cursor.getInt(imageIndex);
                price = cursor.getFloat(priceIndex);
                buyedInt = cursor.getInt(buyedIndex);
                cartInt = cursor.getInt(cartIndex);
                if (buyedInt == 0) { buyed = false;
                } else { buyed = true; }
                if (cartInt == 0) { cart = false;
                } else { cart = true;  }
                //Añadimos el juego a la lista
                Game.m_gameList.add(new Game(id, name, company, TipoConsola.valueOf(type), image, price, buyed, cart));
            } while (cursor.moveToNext());
        }
        database.close();
        return Game.m_gameList;
    }

    public ArrayList<Game> pcGames(){
        Game.m_gameList.clear();
        gameHelper = new GameHelper(this, "gamesdatabase", 1);
        database = gameHelper.getReadableDatabase();
        String query = "SELECT * FROM GAMES WHERE GAME_TYPE='PC'";
        //Copiamos todos los datos de la tabla
        cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            // Situo en la primera posición
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex("GAME_ID");
            int nameIndex = cursor.getColumnIndex("GAME_NAME");
            int companyIndex = cursor.getColumnIndex("GAME_COMPANY");
            int consoleIndex = cursor.getColumnIndex("GAME_TYPE");
            int imageIndex = cursor.getColumnIndex("IMAGE_ID");
            int priceIndex = cursor.getColumnIndex("GAME_PRICE");
            int buyedIndex = cursor.getColumnIndex("BUYED");
            int cartIndex = cursor.getColumnIndex("CART");

            String name, company, type;
            int id, image, buyedInt, cartInt;
            float price;
            boolean buyed, cart;
            do {
                id = cursor.getInt(idIndex);
                name = cursor.getString(nameIndex);
                company = cursor.getString(companyIndex);
                type = cursor.getString(consoleIndex);
                image = cursor.getInt(imageIndex);
                price = cursor.getFloat(priceIndex);
                buyedInt = cursor.getInt(buyedIndex);
                cartInt = cursor.getInt(cartIndex);
                if (buyedInt == 0) { buyed = false;
                } else { buyed = true; }
                if (cartInt == 0) { cart = false;
                } else { cart = true;  }
                //Añadimos el juego a la lista
                Game.m_gameList.add(new Game(id, name, company, TipoConsola.valueOf(type), image, price, buyed, cart));
            } while (cursor.moveToNext());
        }
        database.close();
        return Game.m_gameList;
    }
}