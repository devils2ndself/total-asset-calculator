package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Asset.class}, version = 1)
public abstract class AssetDatabase extends RoomDatabase {
    public abstract AssetDAO assetDao();
}
