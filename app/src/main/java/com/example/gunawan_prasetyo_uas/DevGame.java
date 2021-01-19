package com.example.gunawan_prasetyo_uas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DevGame {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "dev")
    private String dev;
    @ColumnInfo(name = "game")
    private String game;
    @ColumnInfo(name = "added")
    private String added;
    @ColumnInfo(name = "img")
    private String image;

    @Ignore
    public DevGame(String dev, String game, String image, String added){
        this.dev = dev;
        this.game = game;
        this.added = added;
        this.image = image;
    }

    public DevGame(int id, String game, String dev, String image, String added){
        this.id = id;
        this.dev = dev;
        this.game = game;
        this.added = added;
        this.image = image;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getDev() {
        return dev;
    }
    public String getGame() { return game; }
    public String getAdded() { return added; }
    public String getImage() {
        return image;
    }
}