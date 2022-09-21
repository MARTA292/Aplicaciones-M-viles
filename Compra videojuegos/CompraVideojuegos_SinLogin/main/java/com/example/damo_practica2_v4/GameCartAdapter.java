package com.example.damo_practica2_v4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameCartAdapter extends RecyclerView.Adapter<GameCartAdapter.MyHolder> {

    private ArrayList<Game> gamesList;
    private Context context;

    public GameCartAdapter(ArrayList<Game> gamesList, Context context) {
        this.gamesList = gamesList;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_simple_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    //muestra los juegos del carrito
    public void onBindViewHolder(MyHolder holder, int position) {
        // holder --> los elementos de la fila imagen, texto
        Game actualGame = gamesList.get(position);
        holder.txtName.setText(actualGame.name);
        holder.txtCompany.setText(actualGame.company);
        holder.imageView.setImageResource(actualGame.image);
    }

    // Número de filas/elementos que hay
    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName, txtCompany;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name);
            txtCompany = itemView.findViewById(R.id.company);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
