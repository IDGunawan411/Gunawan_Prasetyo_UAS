package com.example.gunawan_prasetyo_uas;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class DataDao {
    @Query("SELECT * FROM Product")
    public abstract List<Product> getAll();

    @Insert
    public abstract void insertAll(Product product);

    @Insert
    public abstract void insertDesc(Product product);

    @Delete
    public abstract void delete(Product product);

    @Query("DELETE FROM product WHERE id = :id")
    public abstract void deleteById(int id);
//    public abstract List<Product> findUsersByNameAndLastName(String name, String last);

    @Query("DELETE FROM Product")
    public abstract void deleteAll();

}
