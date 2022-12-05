package com.example.proyecto_final.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.proyecto_final.entities.Category;
import com.example.proyecto_final.entities.relations.CategoryProducts;
import com.example.proyecto_final.entities.Product;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void insert(Category category);

    @Insert
    void insertProduct(Product product);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM CATEGORY WHERE CATEGORY_NAME = :categoryName")
    void deleteByName(String categoryName);

    @Query("SELECT * FROM CATEGORY ORDER BY CATEGORY_NAME ASC")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM CATEGORY WHERE CATEGORY_NAME = :categoryName")
    @Transaction
    LiveData<List<CategoryProducts>> getCategoryProducts(String categoryName);

}
