package com.example.proyecto_final.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyecto_final.entities.Image;
import com.example.proyecto_final.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductImages {

    @Embedded
    private Product product;

    @Relation(parentColumn = "PRODUCT_ID", entityColumn = "PRODUCT_ID")
    private List<Image> productImages;

    public ProductImages() {
        productImages = new ArrayList<>();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Image> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<Image> productImages) {
        this.productImages = productImages;
    }
}
