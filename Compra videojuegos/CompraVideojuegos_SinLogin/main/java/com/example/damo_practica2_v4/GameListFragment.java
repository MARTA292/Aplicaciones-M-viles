package com.example.damo_practica2_v4;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class GameListFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    //Este fragment es para listar juegos y para los juegos en oferta así que ponemos una variable para diferenciarlos
   private boolean offers;
   private ArrayList<Game> juegos;
   private String[] consoles_type = { "Todos", "PS4", "XBOX", "PC"};

    private SQLiteDatabase database;
    private GameHelper gameHelper;
    private Cursor cursor;

    private View view;
    private ArrayAdapter arrayAdapter;
    private RecyclerView recylerView;
    private GameAdapter gameAdapter;

    public GameListFragment(boolean offers) {
        this.offers = offers;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        juegos = new ArrayList<Game>();
        //Creating the ArrayAdapter instance having the game type list
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, consoles_type);
        setGames(TipoConsola.Todos);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recylerView = view.findViewById(R.id.recycler);
//        gameAdapter = new GameAdapter(juegos, getContext());
//        recylerView.setAdapter(gameAdapter);

        gameHelper = new GameHelper(getContext(), "gamesdatabase", 1) ;
        database = gameHelper.getReadableDatabase();
        Cursor cursor = database.query("GAMES",
                new String[] {"GAME_ID", "GAME_NAME", "GAME_COMPANY", "IMAGE_ID", "GAME_PRICE"},
                null,
                null,
                null, null, null);

        if (offers){
            LinearLayout linearLayout = view.findViewById(R.id.linearLayout);
            linearLayout.setVisibility(View.GONE);
            gamesOffers();
//            gameAdapter = new GameAdapter(juegos, getContext());
//            recylerView.setAdapter(gameAdapter);
        }else {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it
            Spinner spin = (Spinner) view.findViewById(R.id.spinner);
            spin.setOnItemSelectedListener(this);

            //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            spin.setAdapter(arrayAdapter);

            if (juegos.isEmpty()) {
                setGames(TipoConsola.Todos);
            }
        }
        gameAdapter = new GameAdapter(juegos, getContext());
        recylerView.setAdapter(gameAdapter);
        recylerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
        gameAdapter = new GameAdapter(juegos, getContext());
        recylerView.setAdapter(gameAdapter);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void setGames(TipoConsola console_type){
        juegos.clear();
        gameHelper = new GameHelper(getContext(), "gamesdatabase", 1);
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
        Log.v("La query es ", query);
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
    }

    public void gamesOffers(){
        juegos.clear();
        gameHelper = new GameHelper(getContext(), "gamesdatabase", 1);
        database = gameHelper.getReadableDatabase();
        String query = "SELECT * FROM GAMES WHERE GAME_PRICE <= 30";
        Log.d("Console type Offers: ", query);
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
    }
}