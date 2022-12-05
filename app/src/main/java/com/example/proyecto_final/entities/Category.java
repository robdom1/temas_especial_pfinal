package com.example.proyecto_final.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CATEGORY")
public class Category {

    @PrimaryKey
    @ColumnInfo(name = "CATEGORY_NAME")
    @NonNull
    private String name;
    @ColumnInfo(name = "IMG_URL")
    private String imgUrl;

    public Category(String name) {
        this.name = name;
        this.imgUrl = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
