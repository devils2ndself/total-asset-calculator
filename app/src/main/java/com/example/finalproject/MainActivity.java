package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addButton;
    Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}