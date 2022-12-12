package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class NetworkManager {

    public interface NetworkListener {
        void onApiGet(String json);
        void onApiError();
        void onAllAssetsPrices(ArrayList<Asset> assets);
    }

    String newsApiUrl = "https://api.marketaux.com/v1/news/all?language=en&api_token=X4RVFv12cxFnq6YevaFPsALrUHl3SpsEc1gywBdD";
    String stocksApiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&apikey=05IFSASDEYQ0R0YM&symbol=";
    String cryptoApiUrl = "https://api.kucoin.com/api/v1/market/orderbook/level1?symbol=";

    ExecutorService apiExecutor = Executors.newFixedThreadPool(4);
    Handler handler = new Handler(Looper.getMainLooper());
    public NetworkListener listener;

    public void getAllStocksPrices(ArrayList<Asset> assets) {
        apiExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Asset asset : assets) {
                        String urlString;
                        if (asset.type.equals("Stock")) {
                            urlString = stocksApiUrl + asset.name;
                            asset.setUnitPrice(JsonManager.getPriceForStock(getJsonFromUrl(urlString)));
                        } else if (asset.type.equals("Crypto")) {
                            urlString = cryptoApiUrl + asset.name + "-USDT";
                            asset.setUnitPrice(JsonManager.getPriceForCrypto(getJsonFromUrl(urlString)));
                        }
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onAllAssetsPrices(assets);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onApiError();
                }
            }
        });
    }

    public void getStockPrice(String ticker) {
        String url = stocksApiUrl + ticker;
        connect(url);
    }

    public void getCryptoPrice(String ticker) {
        String url = cryptoApiUrl + ticker + "-USDT";
        connect(url);
    }

    public void getNews() {
        connect(newsApiUrl);
    }

    private void connect(String urlString) {
        apiExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final String json = getJsonFromUrl(urlString);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onApiGet(json);
                            Log.d("connect", json);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onApiError();
                }
            }
        });
    }

    private String getJsonFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        InputStream in = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);

        int value = 0;
        String jsonString = "";

        while ( (value = reader.read()) != -1 ){
            char current = (char)value;
            jsonString += current;
        }
        return jsonString;
    }

}
