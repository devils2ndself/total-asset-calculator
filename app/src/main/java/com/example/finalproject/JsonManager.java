package com.example.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonManager {

    public static double getPriceForStock(String json) {
        double price = 0;

        try {
            JSONObject jsonObject = new JSONObject(json);
            price = jsonObject.getJSONObject("Global Quote").getDouble("05. price");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return price;
    }

    public static double getPriceForCrypto(String json) {
        double price = 0;

        try {
            JSONObject jsonObject = new JSONObject(json);
            price = jsonObject.getJSONObject("data").getDouble("price");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return price;
    }
}
