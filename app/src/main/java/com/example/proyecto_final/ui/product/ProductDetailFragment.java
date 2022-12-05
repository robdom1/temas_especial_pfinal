package com.example.proyecto_final.ui.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.proyecto_final.R;
import com.example.proyecto_final.databinding.FragmentProductDetailBinding;
import com.example.proyecto_final.databinding.FragmentProductsBinding;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.ProductImages;

import java.text.DecimalFormat;
import java.util.UUID;


public class ProductDetailFragment extends Fragment {

    private FragmentProductDetailBinding binding;
    private ProductsViewModel mViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        String productId = getArguments().getString("selectedProduct");

        mViewModel.getProductById(UUID.fromString(productId)).observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                setProductValues(product);
            }
        });

        mViewModel.getCartProductQty().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.productDetailCartQty.setText(String.valueOf(integer));
            }
        });

        binding.plusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.increaseQty();
            }
        });

        binding.minusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.decreaseQty();
            }
        });


        return binding.getRoot();
    }

    private void setProductValues(Product product){
        binding.productDetailDescriptionTextView.setText(product.getDescription());

        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        String precioStr = "$" + df.format(product.getPrice());
        binding.productDetailPrice.setText(precioStr);

        UUID productId = product.getProductID();
//        ProductImages productImages = mViewModel.getProductImages(productId).getValue();

        mViewModel.getProductImages(productId).observe(getViewLifecycleOwner(), new Observer<ProductImages>() {
            @Override
            public void onChanged(ProductImages productImages) {
                if(productImages != null){
                    if (!productImages.getProductImages().isEmpty()){
                        Glide.with(ProductDetailFragment.this).load(productImages.getProductImages().get(0)).into(binding.productDetailImageView);
                        return;
                    }
                }
                binding.productDetailImageView.setImageResource(R.drawable.ic_image_not_found);
            }
        });

//        if(productImages.getProductImages().isEmpty()){
//            binding.productDetailImageView.setImageResource(R.drawable.ic_image_not_found);
//        }else if(!productImages.getProductImages().get(0).equals("")){
//            Glide.with(ProductDetailFragment.this).load(productImages.getProductImages().get(0)).into(binding.productDetailImageView);
//        }

//        if(product.getImgUrl().equals("") || product.getImgUrl() == null){
//            Log.d("tag", "onBindViewHolder: " + product.getImgUrl());
//            // Cargar Imagen
//            binding.productDetailImageView.setImageResource(R.drawable.ic_image_not_found);
//
//        }else{
//            Glide.with(ProductDetailFragment.this).load(product.getImgUrl()).into(binding.productDetailImageView);
//        }
    }
}