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

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyHolder> {
    private ArrayList<Game> gamesList;
    private Context context;
    private OnGameListener listener;

    public GameAdapter(ArrayList<Game> gamesList, Context context) {
        this.gamesList = gamesList;
        this.context = context;
        listener = (OnGameListener) context;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_colum_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }
    @Override //muestra tantos elementos como halla
    public void onBindViewHolder(MyHolder holder, int position) {
        // holder --> los elementos de la fila imagen, texto
        Game actualGame = gamesList.get(position);
        holder.txtName.setText(actualGame.name);
        holder.txtPrice.setText(Float.toString(actualGame.price) + " €");
        holder.imageView.setImageResource(actualGame.image);
        if (actualGame.cart == true || actualGame.buyed == true) {
            holder.btnBuy.setEnabled(false);
            if (actualGame.buyed == true) { holder.btnBuy.setText("¡Comprado!");
            } else {holder.btnBuy.setText("Agregado");  }
        } else { holder.btnBuy.setOnClickListener(view -> {
            holder.btnBuy.setText("Agregado");
            holder.btnBuy.setEnabled(false);
            listener.onGameSelected(actualGame);
        });     }
    }

    // Número de juegos a mostrar
    @Override
    public int getItemCount() {
        return gamesList.size();
    }
    public interface OnGameListener { void onGameSelected(Game game);}
    class MyHolder extends RecyclerView.ViewHolder {
        //Establecemos los elementos de la vista
        ImageView imageView;
        TextView txtName, txtPrice;
        Button btnBuy;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name);
            txtPrice = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.image);
            btnBuy = itemView.findViewById(R.id.buy);
        }
    }
}
