package com.example.proyecto_final.ui.product;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_final.R;
import com.example.proyecto_final.databinding.FragmentProductsBinding;


public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ProductsViewModel mViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        binding = FragmentProductsBinding.inflate(inflater, container, false);

        ProductItemAdapter adapter = new ProductItemAdapter(this, R.id.action_nav_product_to_nav_product_detail);
        RecyclerView recyclerView = binding.productRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        mViewModel.getAllProducts().observe(getViewLifecycleOwner(), adapter::setProductList);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.newProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "button pressed", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(ProductsFragment.this)
                        .navigate(R.id.action_nav_product_to_nav_new_product);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}