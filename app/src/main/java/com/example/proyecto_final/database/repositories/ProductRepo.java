package com.example.proyecto_final.database.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.proyecto_final.database.AppDatabase;
import com.example.proyecto_final.database.dao.ProductDAO;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.ProductImages;

import java.util.List;
import java.util.UUID;

public class ProductRepo {
    private ProductDAO productDAO;
    private LiveData<List<Product>> productList;

    public ProductRepo (Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        productDAO = db.productDAO();
        productList = productDAO.getAllProducts();
    }

    // Obtener todos los productos
    public LiveData<List<Product>> getProductList(){
        return productList;
    }

    // Obtener producto por Id
    public LiveData<Product> getProductById(UUID productId){
        return productDAO.getProductById(productId);
    }

    // Obtener imagenes del producto
    public LiveData<ProductImages> getProductImages(UUID productId){
        return productDAO.getProductImages(productId);
    }

    // Insertar nueva categoria
    public void insert(Product product){
        new InsertProductAsyncTask(productDAO).execute(product);
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO asyncProductDao;

        private InsertProductAsyncTask(ProductDAO productDao){
            this.asyncProductDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncProductDao.insert(products[0]);
            return null;
        }
    }


    // Actualizar categoria
    public void update(Product product){
        new UpdateProductAsyncTask(productDAO).execute(product);
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO asyncProductDao;

        private UpdateProductAsyncTask(ProductDAO productDao){
            this.asyncProductDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncProductDao.update(products[0]);
            return null;
        }
    }


    // Eliminar categoria
    public void delete(Product product){
        new DeleteProductAsyncTask(productDAO).execute(product);
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDAO asyncProductDao;

        private DeleteProductAsyncTask(ProductDAO productDao){
            this.asyncProductDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncProductDao.delete(products[0]);
            return null;
        }
    }

    // Eliminar categoria por id
    public void deleteById(UUID productID){
        new DeleteProductByIdAsyncTask(productDAO).execute(productID);
    }

    private static class DeleteProductByIdAsyncTask extends AsyncTask<UUID, Void, Void> {

        private ProductDAO asyncProductDao;

        private DeleteProductByIdAsyncTask(ProductDAO productDao){
            this.asyncProductDao = productDao;
        }

        @Override
        protected Void doInBackground(UUID... productIds) {
            asyncProductDao.deleteById(productIds[0]);
            return null;
        }
    }
}
