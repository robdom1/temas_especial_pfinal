package com.example.proyecto_final.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.proyecto_final.database.dao.CategoryDAO;
import com.example.proyecto_final.database.dao.ProductDAO;
import com.example.proyecto_final.database.dao.UserDAO;
import com.example.proyecto_final.entities.CartProduct;
import com.example.proyecto_final.entities.Category;
import com.example.proyecto_final.entities.Image;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.User;

@Database(entities = {Product.class, Category.class, Image.class, User.class, CartProduct.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static String NAME = "ECOMMERCE_APP";
    private static volatile AppDatabase INSTANCE;

    public abstract ProductDAO productDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract UserDAO userDAO();

    public static synchronized AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(INSTANCE).execute();
        }

    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProductDAO productDao;
        private CategoryDAO categoryDAO;

        private PopulateDBAsyncTask(AppDatabase db) {
            this.productDao = db.productDAO();
            this.categoryDAO = db.categoryDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            categoryDAO.insert(new Category("Cosmetics"));
            categoryDAO.insert(new Category("Electronics"));

            productDao.insert(new Product("prod", 100.00, "Electronics"));
            productDao.insert(new Product("prod2", 200.00, "Electronics"));
            productDao.insert(new Product("prod3", 200.00, "Cosmetics"));
            return null;
        }
    }
}
