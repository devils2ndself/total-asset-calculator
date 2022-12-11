package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssetDAO {

    @Query("SELECT * FROM Asset")
    List<Asset> getAll();

    @Query("SELECT * FROM Asset WHERE type == :type")
    List<Asset> getByType(String type);

    @Insert
    void insert(Asset asset);

    @Delete
    void delete(Asset asset);
}
