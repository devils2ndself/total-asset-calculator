package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AssetsListActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener {

    StocksCryptoRecyclerAdapter stocksAdapter;
    StocksCryptoRecyclerAdapter cryptoAdapter;

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_list);

        dbManager = ((App) getApplication()).dbManager;
        DatabaseManager.getDb(this);
        dbManager.listener = this;
        dbManager.getAllAssetsAsync();
    }

    // Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.list_menu_add) {
            Intent addIntent = new Intent(this, AddAssetActivity.class);
            startActivity(addIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetAll(List<Asset> assets) {
        // Stocks
        ArrayList<Asset> stockAssets = new ArrayList<>(assets);
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
        ArrayList<Asset> cryptoAssets = new ArrayList<>(assets);
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

        // Other?
    }

    @Override
    public void onInsert() { }
}