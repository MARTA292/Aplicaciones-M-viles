package com.example.damo_practica2_v4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements GameAdapter.OnGameListener {
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private SQLiteDatabase database;
    private GameHelper gameHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Componente para la toobar
        androidx.appcompat.widget.Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("GameShop");

        //Para establecer el drawer layout que hay en el activiy_main
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                tool,
                0,
                0
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Para cambiar de fragment
        fragmentManager =  getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_site, new HomeFragment());
        fragmentTransaction.commit();

        //Componente NavigationView (menú lateral)
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationdrawer_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //En lugar de cambiar de actividad vamos a cambiar de fragment
                fragmentTransaction = fragmentManager.beginTransaction();
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_games :
//                Intent intent = new Intent(this, GameListActivity.class);
//                startActivity(intent);
                        fragmentTransaction.replace(R.id.fragment_site, new GameListFragment(false));
                        getSupportActionBar().setTitle("Comprar Juegos");
                        break;
                    case R.id.navigation_offers:
//                intent = new Intent(this, OffersActivity.class);
//                startActivity(intent);
                        fragmentTransaction.replace(R.id.fragment_site, new GameListFragment(true));
                        getSupportActionBar().setTitle("Juegos en oferta");
                        break;
                    case R.id.navigation_cart:
//                intent = new Intent(this, CartActivity.class);
//                startActivity(intent);
                        fragmentTransaction.replace(R.id.fragment_site, new CartFragment());
                        getSupportActionBar().setTitle("Comprar Juegos");
                        break;
                    case R.id.navigation_home:
//                        Intent intent = new Intent(null, MainActivity.class);
//                        startActivity(intent);
                        fragmentTransaction.replace(R.id.fragment_site, new HomeFragment());
                        getSupportActionBar().setTitle("GameShop");
                        break;
                }
                fragmentTransaction.commit();
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onGameSelected(Game game) {
        gameHelper = new GameHelper(getApplicationContext(), "gamesdatabase", 1);
        database = gameHelper.getWritableDatabase();
        //Al darle al botón de comprar se le cambia el booleano
        String query = "UPDATE GAMES SET CART = 1 WHERE GAME_ID = "+ game.id;
        Log.d("La query al comprar es" , query);
        database.execSQL("UPDATE GAMES SET CART = 1 WHERE GAME_ID = "+ game.id);
        database.close();
    }
}