package com.example.proyecto_final.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyecto_final.database.repositories.UserRepo;
import com.example.proyecto_final.entities.CartProduct;
import com.example.proyecto_final.entities.User;
import com.example.proyecto_final.entities.relations.UserCart;

import java.util.List;
import java.util.UUID;

public class UserViewmodel extends AndroidViewModel {
    private UserRepo userRepo;
    private final MutableLiveData<User> currentUser;

    public UserViewmodel(Application application){
        super(application);
        currentUser = new MutableLiveData<>();
        userRepo = new UserRepo(application);
    }

    public void insert(User user){
        userRepo.insert(user);
    }

    public void update(User user){
        userRepo.update(user);
    }

    public void insertCartProduct(CartProduct cartProduct){
        userRepo.insertCartProduct(cartProduct);
    }

    public void updateCartProduct(CartProduct cartProduct){
        userRepo.updateCartProduct(cartProduct);
    }

    public LiveData<List<CartProduct>> getCartProduct(String userEmail, UUID productId){
        return userRepo.getCartProduct(userEmail, productId);
    }

    public LiveData<UserCart> getUserCart(String userEmail){
        return userRepo.getUserCart(userEmail);
    }

    public Boolean authenticate(String email, String password){

        return userRepo.auth(email,password);

//        currentUser.setValue(authUser);
    }

    public LiveData<User> getUser(String userEmail){
        return userRepo.getUser(userEmail);
    }

    public MutableLiveData<User> getCurrentUser(){
        return this.currentUser;
    }

    public void setCurrentUser(User user){
        currentUser.postValue(user);
    }

}
