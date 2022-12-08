package com.example.proyecto_final.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.proyecto_final.entities.CartProduct;
import com.example.proyecto_final.entities.User;
import com.example.proyecto_final.entities.relations.UserCart;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDAO {

    @Insert(onConflict = REPLACE)
    void insert(User user);

    @Insert
    void insertCartProduct(CartProduct cartProduct);

    @Query("SELECT * FROM CART_PRODUCT where USER_EMAIL = :userEmail and PRODUCT_ID = :productId")
    LiveData<List<CartProduct>> getCartProduct(String userEmail, UUID productId);

    @Update
    void updateCartProduct(CartProduct cartProduct);

    @Update
    void update(User user);

    @Query("SELECT * FROM USER WHERE EMAIL = :userEmail")
    LiveData<User> getUser(String userEmail);

    @Query("SELECT * FROM USER WHERE EMAIL = :userEmail and PASSWORD = :userPass")
    LiveData<List<User>> auth (String userEmail, String userPass);

    @Query("SELECT * FROM USER WHERE EMAIL = :userEmail")
    @Transaction
    LiveData<UserCart> getUserCart(String userEmail);

}
