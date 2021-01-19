package com.example.gunawan_prasetyo_uas;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DevGame.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
}
