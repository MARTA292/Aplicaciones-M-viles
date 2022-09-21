package com.example.damo_practica2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GameListFragment extends ListFragment {
    private ListView listView;
    private GameListAdapter adapter;
    //private GameColumAdapter adapter;

    private SQLiteDatabase database;
    private GameHelper gameHelper;
    private Cursor cursor;

    public GameListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
//        listView = (ListView)getView().findViewById(R.id.list);
//
//        adapter = new GameListAdapter(getActivity(),  GameHelper.getAllGame());
//        listView.setAdapter(adapter);
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"NAME"},
                new int[] {android.R.id.text1},
                0);
        setListAdapter(listAdapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list, container, false);
    }
}