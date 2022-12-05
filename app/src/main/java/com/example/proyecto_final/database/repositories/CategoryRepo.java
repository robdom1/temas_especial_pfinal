package com.example.proyecto_final.database.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.proyecto_final.database.AppDatabase;
import com.example.proyecto_final.database.dao.CategoryDAO;
import com.example.proyecto_final.entities.Category;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.CategoryProducts;

import java.util.List;

public class CategoryRepo {

    private CategoryDAO categoryDAO;
    private LiveData<List<Category>> categoryList;

    public CategoryRepo (Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        categoryDAO = db.categoryDAO();
        categoryList = categoryDAO.getAllCategories();
    }

    // Obtener todas las categorias
    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }


    // Insertar nueva categoria
    public void insert(Category category){
        new InsertCategoryAsyncTask(categoryDAO).execute(category);
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDAO asyncCategoryDao;

        private InsertCategoryAsyncTask(CategoryDAO categoryDao){
            this.asyncCategoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncCategoryDao.insert(categories[0]);
            return null;
        }
    }

    // Isertar un producto en la categoria
    public void insertProduct(Product product){
        new InsertCategoryProductAsyncTask(categoryDAO).execute(product);
    }

    private static class InsertCategoryProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private CategoryDAO asyncCategoryDao;

        private InsertCategoryProductAsyncTask(CategoryDAO categoryDao){
            this.asyncCategoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncCategoryDao.insertProduct(products[0]);
            return null;
        }
    }


    // Actualizar categoria
    public void update(Category category){
        new UpdateCategoryAsyncTask(categoryDAO).execute(category);
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDAO asyncCategoryDao;

        private UpdateCategoryAsyncTask(CategoryDAO categoryDao){
            this.asyncCategoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncCategoryDao.update(categories[0]);
            return null;
        }
    }


    // Eliminar categoria
    public void delete(Category category){
        new DeleteCategoryAsyncTask(categoryDAO).execute(category);
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDAO asyncCategoryDao;

        private DeleteCategoryAsyncTask(CategoryDAO categoryDao){
            this.asyncCategoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncCategoryDao.delete(categories[0]);
            return null;
        }
    }

    // Eliminar categoria por nombre
    public void deleteByName(String categoryName){
        new DeleteCategoryByNameAsyncTask(categoryDAO).execute(categoryName);
    }

    private static class DeleteCategoryByNameAsyncTask extends AsyncTask<String, Void, Void> {

        private CategoryDAO asyncCategoryDao;

        private DeleteCategoryByNameAsyncTask(CategoryDAO categoryDao){
            this.asyncCategoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(String... categoryNames) {
            asyncCategoryDao.deleteByName(categoryNames[0]);
            return null;
        }
    }

    // Obtener productos de una categoria por nombre
    public LiveData<List<CategoryProducts>> getCategoryProducts(String categoryName){
        return categoryDAO.getCategoryProducts(categoryName);
    }






}
