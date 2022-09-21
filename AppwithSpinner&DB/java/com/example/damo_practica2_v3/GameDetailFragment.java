package com.example.damo_practica2_v3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GameDetailFragment extends Fragment {

    private long m_gameId;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_detail, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        View v = getView();

        if (v != null)
        {
            ((ImageView)v.findViewById(R.id.imageView)).setImageResource(R.drawable.creedance);
            ((TextView)v.findViewById(R.id.name)).setText("Pendulum");
            ((TextView)v.findViewById(R.id.description)).setText("1970");
            ((TextView)v.findViewById(R.id.year)).setText("1.\t\"Pagan Baby\"\n2.\t\"Sailor's Lament\"");
            ((TextView)v.findViewById(R.id.company)).setText("");
        }
    }

    public void setGame(long id)
    {
        m_gameId = id;
    }
}