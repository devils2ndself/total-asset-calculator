package com.example.finalproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {

    interface DatabaseListener {
        void onGetAll(List<Asset> assets);
        void onGetById(Asset asset);
        void onInsert();
        void onUpdate();
        void onDelete();
    }

    public DatabaseListener listener;

    private static AssetDatabase db;

    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);

    Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public List<Asset> allAssetsFromDb;

    private static void buildDatabase(Context context) {
        db = Room.databaseBuilder(context, AssetDatabase.class, "asset_database").build();
    }

    public static AssetDatabase getDb(Context context) {
        if (db == null) { buildDatabase(context); }
        return db;
    }

    public void insertAssetAsync(Asset asset) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.assetDao().insert(asset);
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onInsert();
                    }
                });
            }
        });
    }

    public void getAllAssetsAsync() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Asset> assets = db.assetDao().getAll();
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onGetAll(assets);
                    }
                });
            }
        });
    }

    public void getAssetByIdAsync(int id) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Asset assets = db.assetDao().getById(id);
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onGetById(assets);
                    }
                });
            }
        });
    }

    public void deleteAssetAsync(Asset asset) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.assetDao().delete(asset);
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDelete();
                    }
                });
            }
        });
    }

    public void updateAssetAsync(Asset asset) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.assetDao().update(asset);
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onUpdate();
                    }
                });
            }
        });
    }
}
