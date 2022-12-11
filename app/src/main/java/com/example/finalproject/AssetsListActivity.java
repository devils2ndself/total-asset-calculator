package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class AssetsListActivity extends AppCompatActivity {

    StocksCryptoRecyclerAdapter stocksAdapter;
    StocksCryptoRecyclerAdapter cryptoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_list);

        // Stocks
        ArrayList<Asset> stockAssets = new ArrayList<>(App.assets);
        stockAssets.removeIf(asset -> !asset.type.equals("Stock"));
        stocksAdapter = new StocksCryptoRecyclerAdapter(this, stockAssets, new StocksCryptoRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AssetsListActivity.this, stocksAdapter.getItem(position).name, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView stocksRecycler = findViewById(R.id.stocks_recycler);
        stocksRecycler.setLayoutManager(new LinearLayoutManager(this));
        stocksRecycler.setAdapter(stocksAdapter);

        // Crypto
        ArrayList<Asset> cryptoAssets = new ArrayList<>(App.assets);
        cryptoAssets.removeIf(asset -> !(asset.type.equals("Crypto")));
        cryptoAdapter = new StocksCryptoRecyclerAdapter(this, cryptoAssets, new StocksCryptoRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AssetsListActivity.this, cryptoAdapter.getItem(position).name, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView cryptoRecycler = findViewById(R.id.crypto_recycler);
        cryptoRecycler.setLayoutManager(new LinearLayoutManager(this));
        cryptoRecycler.setAdapter(cryptoAdapter);
    }
}