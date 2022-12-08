package com.example.proyecto_final.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyecto_final.entities.CartProduct;
import com.example.proyecto_final.entities.Product;

import java.util.List;

public class CartProductWithProducts {

    @Embedded
    private CartProduct cartProduct;
    @Relation(entity = Product.class,
        parentColumn = "PRODUCT_ID", entityColumn = "PRODUCT_ID")
    private List<Product> products;

    public CartProduct getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(CartProduct cartProduct) {
        this.cartProduct = cartProduct;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
