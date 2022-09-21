package com.example.damo_practica2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        GameDetailFragment fragment = (GameDetailFragment) getSupportFragmentManager().findFragmentById(R.id.game_detail_fragment);
        long id = getIntent().getExtras().getLong("gameId");

        fragment.setGame(id);
    }
}
