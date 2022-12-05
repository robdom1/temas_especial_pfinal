package com.example.proyecto_final.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "IMAGE")
public class Image {
    @PrimaryKey
    @ColumnInfo(name = "IMG_URL")
    @NonNull
    private String imgUrl;
    @ColumnInfo(name = "PRODUCT_ID")
    private UUID productId;

    public Image(UUID productId) {
        this.productId = productId;
        this.imgUrl = "";
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
