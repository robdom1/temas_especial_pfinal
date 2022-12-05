package com.example.proyecto_final.ui.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final.R;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.ProductImages;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductItemAdapter  extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder>{

    private List<Product> productList = new ArrayList<>();
    private int nav_action;
    private Fragment mContext;

    public ProductItemAdapter(Fragment context, int nav_action){
        this.nav_action = nav_action;
        this.mContext = context;
    }

    public void setProductList(List<Product> list){
        productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_item, parent, false);
        return new ProductItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {

        Product actualProduct = productList.get(position);

//        holder.productId.setText(actualProduct.getProductID().toString());
        holder.productDescription.setText(actualProduct.getDescription());
//        holder.productCategory.setText(actualProduct.getCategoryName());

        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        String precioStr = "$" + df.format(actualProduct.getPrice());
        holder.productPrice.setText(precioStr);

        ProductsViewModel productsViewModel = new ViewModelProvider(mContext).get(ProductsViewModel.class);

        UUID productId = actualProduct.getProductID();
//        ProductImages productImages = productsViewModel.getProductImages(productId).getValue();

        productsViewModel.getProductImages(productId).observe(mContext.getViewLifecycleOwner(), new Observer<ProductImages>() {
            @Override
            public void onChanged(ProductImages productImages) {
                if(productImages != null){
                    if (!productImages.getProductImages().isEmpty()){
                        Glide.with(mContext).load(productImages.getProductImages().get(0)).into(holder.productImage);
                        return;
                    }
                }
                holder.productImage.setImageResource(R.drawable.ic_image_not_found);
            }
        });

//        if(productImages.getProductImages().isEmpty()){
//            holder.productImage.setImageResource(R.drawable.ic_image_not_found);
//        }else if(!productImages.getProductImages().get(0).equals("")){
//            Glide.with(mContext).load(productImages.getProductImages().get(0)).into(holder.productImage);
//        }

//        if(actualProduct.getImgUrl().equals("") || actualProduct.getImgUrl() == null){
//            Log.d("tag", "onBindViewHolder: " + actualProduct.getImgUrl());
//            // Cargar Imagen
//            holder.productImage.setImageResource(R.drawable.ic_image_not_found);
//
//        }else{
//            Glide.with(mContext).load(actualProduct.getImgUrl()).into(holder.productImage);
//        }

        holder.productItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("selectedProduct", actualProduct.getProductID().toString());
                NavHostFragment.findNavController(mContext)
                        .navigate(nav_action, data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder{

//        TextView productId;
        TextView productDescription;
        TextView productPrice;
//        TextView productCategory;
        ImageView productImage;

        CardView productItemContainer;

        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);
//            productId = itemView.findViewById(R.id.product_id_textView);
            productDescription = itemView.findViewById(R.id.product_description_textView);
            productPrice = itemView.findViewById(R.id.product_price_textView);
//            productCategory = itemView.findViewById(R.id.product_category_textView);
            productImage = itemView.findViewById(R.id.product_imageView);
            productItemContainer = itemView.findViewById(R.id.product_container);
        }
    }
}
