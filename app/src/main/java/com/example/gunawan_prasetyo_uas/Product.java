package com.example.gunawan_prasetyo_uas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "nama")
    private String nama;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "img")
    private String image;
    @Ignore
    public Product(String nama, String desc, String image){
        this.nama = nama;
        this.desc = desc;
        this.image = image;
    }

    public Product(int id, String desc, String nama, String image){
        this.id = id;
        this.nama = nama;
        this.desc = desc;
        this.image = image;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNama() {
        return nama;
    }
    public String getDesc() { return desc; }
    public String getImage() {
        return image;
    }
}
