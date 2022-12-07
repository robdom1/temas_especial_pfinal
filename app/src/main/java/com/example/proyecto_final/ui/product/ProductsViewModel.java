package com.example.proyecto_final.ui.product;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_final.database.repositories.ProductRepo;
import com.example.proyecto_final.entities.Image;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.ProductImages;

import java.util.List;
import java.util.UUID;

public class ProductsViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> cartProductQty;
    private LiveData<List<Product>> allProducts;
    private ProductRepo productRepo;

    public ProductsViewModel(Application application){
        super(application);
        cartProductQty = new MutableLiveData<>();
        cartProductQty.setValue(1);
        productRepo = new ProductRepo(application);
        allProducts = productRepo.getProductList();

    }

    public LiveData<Product> getProductById(UUID productId){
        return productRepo.getProductById(productId);
    }

    public LiveData<ProductImages> getProductImages(UUID productId){
        return productRepo.getProductImages(productId);
    }

    public MutableLiveData<Integer> getCartProductQty() {
        return cartProductQty;
    }

    public void increaseQty(){
        cartProductQty.postValue(cartProductQty.getValue() + 1);
    }

    public void decreaseQty(){
        int actualValue = cartProductQty.getValue();
        if(actualValue > 1){
            cartProductQty.postValue(actualValue - 1);
        }
    }

    public LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }

    public void insert(Product product){
        productRepo.insert(product);
    }

    public void insertImage(Image image){
        productRepo.insertImage(image);
    }

    public void update(Product product){
        productRepo.update(product);
    }

    public void delete(Product product){
        productRepo.delete(product);
    }

    public void deleteById(UUID productId){
        productRepo.deleteById(productId);
    }

//    public void insertImages(ProductImages productImages){
//        productRepo.insertImages(productImages);
//    }





}