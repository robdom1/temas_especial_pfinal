package com.example.proyecto_final.ui.cart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final.R;
import com.example.proyecto_final.entities.CartProduct;
import com.example.proyecto_final.entities.Category;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.entities.relations.CartProductWithProducts;
import com.example.proyecto_final.entities.relations.ProductImages;
import com.example.proyecto_final.ui.category.CategoryFragment;
import com.example.proyecto_final.ui.product.ProductsViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private List<CartProductWithProducts> cartList = new ArrayList<>();
    private CartFragment mContext;

    public CartAdapter(CartFragment mContext) {
        this.mContext = mContext;
    }

    public void setCartList(List<CartProductWithProducts> list){
        this.cartList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {

        CartProductWithProducts aux = cartList.get(position);
        Product actualProduct = aux.getProducts().get(0);
        CartProduct actualCartProduct = aux.getCartProduct();

        holder.cartItemName.setText(actualProduct.getDescription());
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        String precioStr = "$" + df.format(actualProduct.getPrice());
        holder.cartItemPrice.setText("Price: "+ precioStr);
        holder.cartItemQty.setText("Quantity: " + actualCartProduct.getProductQty());

        Double total = actualProduct.getPrice() * actualCartProduct.getProductQty();
        String totalStr = "$" + df.format(total);
        holder.cartItemTotal.setText(totalStr);


        ProductsViewModel productsViewModel = new ViewModelProvider(mContext).get(ProductsViewModel.class);

        UUID productId = actualProduct.getProductID();
//        ProductImages productImages = productsViewModel.getProductImages(productId).getValue();

        productsViewModel.getProductImages(productId).observe(mContext.getViewLifecycleOwner(), new Observer<ProductImages>() {
            @Override
            public void onChanged(ProductImages productImages) {
                if(productImages != null){
                    if (!productImages.getProductImages().isEmpty()){
                        Log.d("TAG", "onChanged: " + productImages.getProductImages().get(0).getImgUrl());
                        Glide.with(mContext)
                                .load(productImages.getProductImages().get(0).getImgUrl())
                                .error(R.drawable.ic_image_not_found)
                                .into(holder.cartItemImage);
                        return;
                    }
                    holder.cartItemImage.setImageResource(R.drawable.ic_image_not_found);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder{

        ImageView cartItemImage;
        TextView cartItemName;
        TextView cartItemPrice;
        TextView cartItemTotal;
        TextView cartItemQty;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cart_imageView);
            cartItemName = itemView.findViewById(R.id.cart_name_textview);
            cartItemPrice = itemView.findViewById(R.id.cart_price_textView);
            cartItemTotal = itemView.findViewById(R.id.cart_total_textView);
            cartItemQty = itemView.findViewById(R.id.cart_qty_textView);
        }
    }
}
