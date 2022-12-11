package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addButton;
    Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (App.assets.size() == 0) {
            App.assets.add(new Asset("Stock", "VOO", 12));
            App.assets.add(new Asset("Stock", "VTI", 7));
            App.assets.add(new Asset("Stock", "KO", 5));
            App.assets.add(new Asset("Crypto", "BTC", 2.5));
        }

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
}