package com.example.proyecto_final.ui.product;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_final.R;
import com.example.proyecto_final.databinding.FragmentProductsBinding;
import com.example.proyecto_final.entities.Product;

import java.util.ArrayList;
import java.util.List;


public class ProductsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private FragmentProductsBinding binding;
    private ProductsViewModel mViewModel;
    private RecyclerView recyclerView;
    private ProductItemAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        adapter = new ProductItemAdapter(this, R.id.action_nav_product_to_nav_product_detail);
        recyclerView = binding.productRecyclerView;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return false;
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.main, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(ProductsFragment.this);
//        searchView.setQueryHint("Search");
//    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        mViewModel.getAllProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                List<Product> filteredlist = new ArrayList<Product>();
                // running a for loop to compare elements.
                for (Product item : products) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                        // if the item is matched we are
                        // adding it to our filtered list.
                        filteredlist.add(item);
                    }
                }
                adapter.setProductList(filteredlist);
            }
        });



    }
}