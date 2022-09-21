package com.example.damo_practica2;

import java.util.ArrayList;

public class Game {
    public String name, company;
    public TipoConsola type;
    public int id, image;
    public float price;
    public boolean buyed, cart;

    public static final ArrayList<Game> m_gameList = new ArrayList<>();

    public Game(int id, String name, String company, TipoConsola type, int image, float price, boolean buyed, boolean cart) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.type = type;
        this.image = image;
        this.price = price;
        this.buyed = buyed;
        this.cart = cart;
    }
}
