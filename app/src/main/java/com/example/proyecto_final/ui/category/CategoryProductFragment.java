package com.example.proyecto_final.ui.category;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyecto_final.R;
import com.example.proyecto_final.databinding.FragmentCategoryBinding;import com.example.proyecto_final.databinding.FragmentCategoryProductBinding;
import com.example.proyecto_final.entities.relations.CategoryProducts;
import com.example.proyecto_final.ui.product.ProductItemAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CategoryProductFragment extends Fragment {

    private FragmentCategoryProductBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CategoryViewModel categoryViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentCategoryProductBinding.inflate(inflater, container, false);

        ProductItemAdapter adapter = new ProductItemAdapter(this, R.id.action_nav_category_product_to_nav_product_detail);

        RecyclerView recyclerView = binding.productRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        String category = getArguments().getString("selectedCategory");

        categoryViewModel.getCategoryProducts(category).observe(getViewLifecycleOwner(), new Observer<List<CategoryProducts>>() {
            @Override
            public void onChanged(List<CategoryProducts> categoryProducts) {
                adapter.setProductList(categoryProducts.get(0).getCategoryProducts());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}