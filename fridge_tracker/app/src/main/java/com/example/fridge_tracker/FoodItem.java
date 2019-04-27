package com.example.fridge_tracker;

public class FoodItem {

    private String name;
    private String expiration;
    private String quantity;

    public FoodItem(String name, String quantity, String expiration){
        this.name=name;
        this.expiration=expiration;
        this.quantity=quantity;
    }
}
