package com.example.gunawan_prasetyo_uas;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class DataDao {
    @Query("SELECT * FROM DevGame")
    public abstract List<DevGame> getAll();

    @Insert
    public abstract void insertAll(DevGame devGame);

    @Insert
    public abstract void insertDesc(DevGame devGame);

    @Delete
    public abstract void delete(DevGame devGame);

    @Query("DELETE FROM DevGame WHERE id = :id")
    public abstract void deleteById(int id);

    @Query("DELETE FROM DevGame")
    public abstract void deleteAll();

}
