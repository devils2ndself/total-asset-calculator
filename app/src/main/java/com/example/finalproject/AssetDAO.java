package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssetDAO {

    @Query("SELECT * FROM Asset")
    List<Asset> getAll();

    @Query("SELECT * FROM Asset WHERE id == :id")
    Asset getById(int id);

    @Insert
    void insert(Asset asset);

    @Update
    void update(Asset asset);

    @Delete
    void delete(Asset asset);
}
