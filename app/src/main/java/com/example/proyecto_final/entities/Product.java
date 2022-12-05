package com.example.proyecto_final.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "PRODUCT")
public class Product {
    @PrimaryKey
    @ColumnInfo(name = "PRODUCT_ID")
    @NonNull
    private UUID productID;
    @ColumnInfo(name = "DESCRIPTIOM")
    private String description;
    @ColumnInfo(name = "PRICE")
    private Double price;
//    @ColumnInfo(name = "IMG_URL")
//    private String imgUrl;
    @ColumnInfo(name = "CATEGORY_NAME")
    private String categoryName;

    public Product(String description, Double price, String categoryName) {
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
//        this.imgUrl="";
        this.productID = UUID.randomUUID();
    }

    public UUID getProductID() {
        return productID;
    }

    public void setProductID(UUID productID) {
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

//    public String getImgUrl() {
//        return imgUrl;
//    }
//
//    public void setImgUrl(String imgUrl) {
//        this.imgUrl = imgUrl;
//    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
