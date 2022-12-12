package com.example.finalproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonManager {

    public static ArrayList<NewsArticle> getNewsArticles(String json) {
        ArrayList<NewsArticle> news = new ArrayList<>(0);

        try {
            JSONArray newsArray = new JSONObject(json).getJSONArray("data");
            for (int i = 0 ; i < newsArray.length(); i++){
                JSONObject jsonArticle = newsArray.getJSONObject(i);
                NewsArticle newsObject = new NewsArticle(
                        jsonArticle.getString("title"),
                        jsonArticle.getString("description"),
                        jsonArticle.getString("url"),
                        jsonArticle.getString("image_url"),
                        jsonArticle.getString("published_at")
                );
                news.add(newsObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return news;
    }

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
