package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AssetDetailsActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener {

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);

        Bundle extras = getIntent().getExtras();

        dbManager = ((App) getApplication()).dbManager;
        DatabaseManager.getDb(this);
        dbManager.listener = this;
        dbManager.getAssetByIdAsync(extras.getInt("id"));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onGetById(Asset asset) {
        TextView nameTextview = findViewById(R.id.asset_details_name);
        nameTextview.setText(asset.name);

        TextView typeTextview = findViewById(R.id.asset_details_type);
        typeTextview.setText(asset.type);

        EditText quantityEdittext = (EditText) findViewById(R.id.asset_details_quantity_edit);
        quantityEdittext.setText(String.valueOf(asset.quantity));

        TextView priceTextview = findViewById(R.id.asset_details_price);
        priceTextview.setText(String.format("%.2f", asset.unitPrice));

        Button deleteButton = (Button) findViewById(R.id.asset_details_delete_button);
        deleteButton.setOnClickListener(view -> {
            try {
                deleteButton.setEnabled(false);
                dbManager.deleteAssetAsync(asset);
            } catch (Exception e) {
                deleteButton.setEnabled(true);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Button updateButton = (Button) findViewById(R.id.asset_details_update_button);
        updateButton.setOnClickListener(view -> {
            try {
                updateButton.setEnabled(false);

                String quantityString = quantityEdittext.getText().toString();
                if (quantityString.isEmpty()) { throw new Exception(getString(R.string.quantity_empty_error)); }
                double quantity = Double.parseDouble(quantityString);

                asset.quantity = quantity;
                dbManager.updateAssetAsync(asset);
            } catch (Exception e) {
                updateButton.setEnabled(true);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdate() {
        startActivity(new Intent(this, AssetsListActivity.class));
    }

    @Override
    public void onDelete() {
        startActivity(new Intent(this, AssetsListActivity.class));
    }

    @Override
    public void onGetAll(List<Asset> assets) { }

    @Override
    public void onInsert() { }
}