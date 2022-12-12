package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener, NetworkManager.NetworkListener {

    TextView totalTextView;

    Button addButton;
    Button viewButton;

    DatabaseManager dbManager;
    NetworkManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalTextView = findViewById(R.id.main_total_number);

        dbManager = ((App) getApplication()).dbManager;
        DatabaseManager.getDb(this);
        dbManager.listener = this;
        dbManager.getAllAssetsAsync();

        apiManager = ((App) getApplication()).apiManager;
        apiManager.listener = this;

        addButton = findViewById(R.id.main_add_button);
        viewButton = findViewById(R.id.main_view_button);

        addButton.setOnClickListener(view -> {
            Intent addIntent = new Intent(this, AddAssetActivity.class);
            startActivity(addIntent);
        });
        viewButton.setOnClickListener(view -> {
            Intent viewIntent = new Intent(this, AssetsListActivity.class);
            startActivity(viewIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_view:
                Intent viewIntent = new Intent(this, AssetsListActivity.class);
                startActivity(viewIntent);
                return true;
            case R.id.menu_add:
                Intent addIntent = new Intent(this, AddAssetActivity.class);
                startActivity(addIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetAll(List<Asset> assets) {
        apiManager.getAllStocksPrices((ArrayList<Asset>) assets);
    }

    @Override
    public void onGetById(Asset asset) { }

    @Override
    public void onInsert() { }

    @Override
    public void onUpdate() { }

    @Override
    public void onDelete() { }

    @Override
    public void onApiGet(String json) { }

    @Override
    public void onApiError() { }

    @SuppressLint("DefaultLocale")
    @Override
    public void onAllAssetsPrices(ArrayList<Asset> assets) {
        double total = 0;
        for (Asset asset : assets) {
            total += asset.getTotal();
        }
        totalTextView.setText(String.format("%.2f", total));
    }
}