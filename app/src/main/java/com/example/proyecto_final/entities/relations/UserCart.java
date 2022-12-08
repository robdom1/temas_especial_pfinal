package com.example.proyecto_final.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyecto_final.entities.CartProduct;
import com.example.proyecto_final.entities.User;

import java.util.List;

public class UserCart {
    @Embedded
    private User user;

    @Relation(entity = CartProduct.class, parentColumn = "EMAIL", entityColumn = "USER_EMAIL")
    private List<CartProductWithProducts> cartProducts;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProductWithProducts> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductWithProducts> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
