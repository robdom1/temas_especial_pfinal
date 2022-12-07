package com.example.proyecto_final.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto_final.entities.Image;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.ProductImages;

import java.util.List;
import java.util.UUID;

@Dao
public interface ProductDAO {

    @Insert
    void insert(Product product);

    @Insert
    void insertImage(Image image);

    @Update
    void update (Product product);


    @Delete
    void delete (Product product);

    @Query("DELETE FROM PRODUCT WHERE PRODUCT_ID = :productId")
    void deleteById(UUID productId);

    @Query("SELECT * FROM PRODUCT WHERE PRODUCT_ID = :productId")
    LiveData<Product> getProductById(UUID productId);

    @Query("SELECT * FROM PRODUCT ORDER BY PRODUCT_ID")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM PRODUCT WHERE PRODUCT_ID = :productId")
    LiveData<ProductImages> getProductImages(UUID productId);
}
