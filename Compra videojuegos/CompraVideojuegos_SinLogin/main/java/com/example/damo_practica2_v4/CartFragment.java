package com.example.damo_practica2_v4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartFragment extends Fragment implements View.OnClickListener{
    private View view;
    private RecyclerView recylerView;
    private GameCartAdapter gameAdapter;
    private ArrayList<Game> juegos;

    private SQLiteDatabase database;
    private GameHelper gameHelper;
    private Cursor cursor;

    private TextView txtTotal, txtNoGames;
    private Button btnBuy;

    private int importeTotal = 0;

    public CartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        juegos = new ArrayList<>();
        addGames(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        recylerView = view.findViewById(R.id.simple_recycler);

        txtTotal = view.findViewById(R.id.txt_total);
        txtNoGames = view.findViewById(R.id.txt_no_games);
        btnBuy = view.findViewById(R.id.btn_buy);

        txtTotal.setText(Integer.toString(importeTotal)+"€");
        btnBuy.setOnClickListener(this);
        if (juegos.size() > 0) {
            txtNoGames.setVisibility(View.GONE);
        }

        gameAdapter = new GameCartAdapter(juegos, getContext());
        recylerView.setAdapter(gameAdapter);
        recylerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    }
    @SuppressLint("Range")
    private void addGames(Context context) {
        juegos.clear();
        gameHelper = new GameHelper(context, "gamesdatabase", 1);
        database = gameHelper.getReadableDatabase();
        String query = "SELECT * FROM GAMES WHERE CART = '1'";
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
                if (buyedInt == 0) {buyed = false;
                } else {buyed = true;}
                if (cartInt == 0) { cart = false;
                } else { cart = true; }
                importeTotal += price;
                juegos.add(new Game(id, name, company, TipoConsola.valueOf(type), image, price,
                        buyed, cart));
            } while (cursor.moveToNext());
            Log.d("El importe es ", Integer.toString(importeTotal));
            Log.d("El número de juegos es", Integer.toString(juegos.size()));
        }
        database.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_cart, container, false);
       return view;
    }

    @Override
    public void onClick(View v) {
        database = gameHelper.getWritableDatabase();
        //Cargamos la información por cada juego que compra
        for (Game game:juegos) {
            String query = "UPDATE GAMES SET CART = 0, BUYED = 1 WHERE GAME_ID ="+ game.id;
            Log.d("Query tras comprar ", query);
            database.execSQL("UPDATE GAMES SET CART = 0, BUYED = 1 WHERE GAME_ID ="+ game.id);
        }
        database.close();
        //Escribimos el email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"estrellita.m22@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ticket de compra");
        intent.putExtra(Intent.EXTRA_TEXT, "Su compra se ha procesado, el pago a realizar es de "+ importeTotal +"€");
        intent.setType("message/rfc822");
        startActivity(intent);
        addGames(v.getContext());
    }
}