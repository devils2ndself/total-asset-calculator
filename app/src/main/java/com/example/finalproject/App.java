package com.example.finalproject;

import android.app.Application;

import java.util.ArrayList;

public class App extends Application {
    DatabaseManager dbManager = new DatabaseManager();
    NetworkManager apiManager = new NetworkManager();
}
