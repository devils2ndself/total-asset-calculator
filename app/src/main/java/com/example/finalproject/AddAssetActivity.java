package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class AddAssetActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener, NetworkManager.NetworkListener {

    DatabaseManager dbManager;
    NetworkManager apiManager;
    Asset newAsset;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        // DB Manager
        dbManager = ((App) getApplication()).dbManager;
        DatabaseManager.getDb(this);
        dbManager.listener = this;

        apiManager = ((App) getApplication()).apiManager;
        apiManager.listener = this;

        // Type spinner
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Stock");
        spinnerArray.add("Crypto");
        spinnerArray.add("Cash");
        spinnerArray.add("Bank money");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.add_asset_type);
        spinner.setAdapter(adapter);

        // Add button
        addButton = (Button) findViewById(R.id.add_asset_button);
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

                newAsset = new Asset(type, name, quantity);
                switch (type) {
                    case "Stock":
                        apiManager.getStockPrice(name);
                        break;
                    case "Crypto":
                        apiManager.getCryptoPrice(name);
                        break;
                    default:
                        dbManager.insertAssetAsync(newAsset);
                        break;
                }
            } catch (Exception e) {
                showError(e.getMessage());
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

    @Override
    public void onApiGet(String json) {
        double price;
        boolean ok = true;
        switch (newAsset.type) {
            case "Stock":
                price = JsonManager.getPriceForStock(json);
                if (price <= 0) {
                    ok = false;
                    showError(getString(R.string.add_stock_price_error));
                }
                break;
            case "Crypto":
                price = JsonManager.getPriceForCrypto(json);
                if (price <= 0) {
                    ok = false;
                    showError(getString(R.string.add_crypto_price_error));
                }
                break;
            default:
                break;
        }
        if (ok) dbManager.insertAssetAsync(newAsset);
    }

    void showError(String error) {
        addButton.setEnabled(true);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApiError() {
        showError(getString(R.string.add_crypto_price_error));
    }

    @Override
    public void onAllAssetsPrices(ArrayList<Asset> assets) { }
}