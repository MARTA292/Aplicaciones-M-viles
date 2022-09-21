package com.example.damo_practica2_v3;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class GameListFragment extends ListFragment {

    public GameListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String names[] = new String[GameData.m_gameDataList.size()];
        for (int i = 0; i < GameData.m_gameDataList.size(); i++) {
            names[i] = GameData.m_gameDataList.get(i).m_title;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list, container, false);
    }

    static interface Listener  {
        void itemClicked(long id);
    }

    private Listener m_listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        m_listener = (Listener) context;
    }

    @Override
    public void onListItemClick (ListView list, View item, int position, long id) {
        if (m_listener != null) {
            m_listener.itemClicked(id);
        }
    }


}