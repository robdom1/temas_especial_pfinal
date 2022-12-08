package com.example.proyecto_final.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.UUID;

@Entity(tableName = "CART_PRODUCT", primaryKeys = {"PRODUCT_ID", "USER_EMAIL"})
public class CartProduct {

    @ColumnInfo(name = "PRODUCT_ID")
    @NonNull
    private UUID productId;
    @ColumnInfo(name = "USER_EMAIL")
    @NonNull
    private String userEmail;
    @ColumnInfo(name = "QUANTITY")
    private int productQty;


    public CartProduct(@NonNull UUID productId, @NonNull String userEmail, int productQty) {
        this.productId = productId;
        this.userEmail = userEmail;
        this.productQty = productQty;
    }

    @NonNull
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(@NonNull UUID productId) {
        this.productId = productId;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}
