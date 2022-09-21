package com.example.damo_practica2_v4;

public class Game {
    public String name, company;
    public TipoConsola type;
    public int id, image;
    public float price;
    public boolean buyed, cart;


    public Game(int id, String name, String company, TipoConsola type, int image, float price, boolean buyed, boolean addedToCart) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.type = type;
        this.image = image;
        this.price = price;
        this.buyed = buyed;
        this.cart = addedToCart;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", type of console=" + type.toString() +
                ", image=" + image +
                ", price=" + price +
                ", buyed=" + buyed +
                ", cart=" + cart +
                '}';
    }
}
