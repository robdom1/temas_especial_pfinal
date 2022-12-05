package com.example.proyecto_final.entities.relations;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyecto_final.entities.Category;
import com.example.proyecto_final.entities.Product;

import java.util.List;

public class CategoryProducts {

    @Embedded
    private Category category;

    @Relation(parentColumn = "CATEGORY_NAME", entityColumn = "CATEGORY_NAME")
    private List<Product> categoryProducts;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(List<Product> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }
}
