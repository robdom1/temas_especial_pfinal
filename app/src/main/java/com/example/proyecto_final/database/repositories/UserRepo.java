package com.example.proyecto_final.database.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto_final.database.AppDatabase;
import com.example.proyecto_final.database.dao.UserDAO;
import com.example.proyecto_final.entities.CartProduct;
import com.example.proyecto_final.entities.User;
import com.example.proyecto_final.entities.relations.UserCart;

import java.util.List;
import java.util.UUID;

public class UserRepo {
    private UserDAO userDAO;


    public UserRepo(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        userDAO = db.userDAO();
    }

    // Obtener Usuario
    public LiveData<User> getUser(String userEmail){
        return userDAO.getUser(userEmail);
    }

    // Insertar nuevo usuario
    public void insert(User user){
        new InsertUserAsyncTask(userDAO).execute(user);
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO asyncUserDao;

        public InsertUserAsyncTask(UserDAO asyncUserDao) {
            this.asyncUserDao = asyncUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            asyncUserDao.insert(users[0]);
            return null;
        }
    }

    // Actualizar usuario
    public void update(User user){
        new UpdateUserAsyncTask(userDAO).execute(user);
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO asyncUserDao;

        public UpdateUserAsyncTask(UserDAO asyncUserDao) {
            this.asyncUserDao = asyncUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            asyncUserDao.update(users[0]);
            return null;
        }
    }

    // Insertar Cart Product

    public void insertCartProduct(CartProduct cartProduct){
        new InsertCartProduct(userDAO).execute(cartProduct);
    }


    private static class InsertCartProduct extends AsyncTask<CartProduct, Void, Void>{

        private UserDAO asyncUserDao;

        public InsertCartProduct(UserDAO asyncUserDao) {
            this.asyncUserDao = asyncUserDao;
        }

        @Override
        protected Void doInBackground(CartProduct... cartProducts) {
            asyncUserDao.insertCartProduct(cartProducts[0]);
            return null;
        }
    }

    // Obtener User Cart
    public LiveData<UserCart> getUserCart(String userEmail){
        return userDAO.getUserCart(userEmail);
    }


    public LiveData<List<CartProduct>> getCartProduct(String userEmail, UUID productId){
        return userDAO.getCartProduct(userEmail, productId);
    }


    public void updateCartProduct(CartProduct cartProduct){
        new UpdateCartAsyncTask(userDAO).execute(cartProduct);
    }

    private static class UpdateCartAsyncTask extends AsyncTask<CartProduct, Void, Void> {
        private UserDAO asyncUserDao;

        public UpdateCartAsyncTask(UserDAO asyncUserDao) {
            this.asyncUserDao = asyncUserDao;
        }

        @Override
        protected Void doInBackground(CartProduct... cartProducts) {
            asyncUserDao.updateCartProduct(cartProducts[0]);
            return null;
        }
    }



    public Boolean auth(String userEmail, String userPass){
        User userlist = userDAO.getUser(userEmail).getValue();

        if (userlist == null){
            return false;
        }

        return userlist.getPassword().equals(userPass);

    }
}
