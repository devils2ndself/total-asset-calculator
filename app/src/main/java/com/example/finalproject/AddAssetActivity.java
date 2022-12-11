package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddAssetActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener {

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        // DB Manager
        dbManager = ((App) getApplication()).dbManager;
        DatabaseManager.getDb(this);
        dbManager.listener = this;

        // Type spinner
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Stock");
        spinnerArray.add("Crypto");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.add_asset_type);
        spinner.setAdapter(adapter);

        // Add button
        Button addButton = (Button) findViewById(R.id.add_asset_button);
        EditText nameText = (EditText) findViewById(R.id.add_asset_name);
        EditText quantityText = (EditText) findViewById(R.id.add_asset_quantity);
        addButton.setOnClickListener(view -> {
            try {
                addButton.setEnabled(false);

                String type = spinner.getSelectedItem().toString();
                if (! spinnerArray.contains(type)) { throw new Exception(getString(R.string.invalid_type_error)); }

                String name = nameText.getText().toString().trim();
                if (name.isEmpty()) { throw new Exception(getString(R.string.name_empty_error)); }

                String quantityString = quantityText.getText().toString();
                if (quantityString.isEmpty()) { throw new Exception(getString(R.string.quantity_empty_error)); }
                double quantity = Double.parseDouble(quantityString);

                Asset newAsset = new Asset(type, name, quantity);
                dbManager.insertAssetAsync(newAsset);
            } catch (Exception e) {
                addButton.setEnabled(true);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onGetAll(List<Asset> assets) { }

    @Override
    public void onGetById(Asset asset) { }

    @Override
    public void onInsert() {
        startActivity(new Intent(this, AssetsListActivity.class));
    }

    @Override
    public void onUpdate() { }

    @Override
    public void onDelete() { }
}