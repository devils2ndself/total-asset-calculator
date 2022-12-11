package com.example.finalproject;

import androidx.room.PrimaryKey;

public class Asset {

    public Asset(String type, String name, double quantity) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = 1;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type;

    public double quantity;

    public String name;

    // @Ignore
    public double unitPrice;

    public void setUnitPrice(double price) {
        this.unitPrice = price;
    }

    public double getTotal() {
        return quantity * unitPrice;
    };
}
