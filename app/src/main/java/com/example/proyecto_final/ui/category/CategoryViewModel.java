package com.example.proyecto_final.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_final.database.repositories.CategoryRepo;
import com.example.proyecto_final.entities.Category;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.CategoryProducts;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private LiveData<List<Category>> allCategories;
    private CategoryRepo categoryRepo;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepo = new CategoryRepo(application);
        allCategories = categoryRepo.getCategoryList();
    }



    public void insert(Category category){
        categoryRepo.insert(category);
    }

    public void insertProduct(Product product){
        categoryRepo.insertProduct(product);
    }

    public void update(Category category){
        categoryRepo.update(category);
    }

    public void delete(Category category){
        categoryRepo.delete(category);
    }

    public void deleteByName(String categoryName){
        categoryRepo.deleteByName(categoryName);
    }

    public LiveData<List<CategoryProducts>> getCategoryProducts(String categoryName){
        return categoryRepo.getCategoryProducts(categoryName);
    }

    public LiveData<List<Category>> getAllCategories(){
        return allCategories;
    }


}