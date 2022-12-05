package com.example.proyecto_final.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final.R;
import com.example.proyecto_final.entities.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder> {

    private List<Category> categoryList = new ArrayList<>();
    private CategoryFragment mContext;

    public CategoryItemAdapter(CategoryFragment context){
        this.mContext = context;
    }

    public void setCategoryList(List<Category> list){
        categoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {

        Category actualCategory = categoryList.get(position);

        holder.categoryName.setText(actualCategory.getName());



        if(actualCategory.getImgUrl().equals("") || actualCategory.getImgUrl() == null){
            Log.d("tag", "onBindViewHolder: " + actualCategory.getImgUrl());
            // Cargar Imagen
            holder.categoryImage.setImageResource(R.drawable.ic_image_not_found);

        }else{
            Glide.with(mContext).load(actualCategory.getImgUrl()).into(holder.categoryImage);
        }

        holder.categoryActionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("selectedCategory", actualCategory.getName());
                NavHostFragment.findNavController(mContext)
                        .navigate(R.id.action_nav_category_to_nav_category_product, data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryItemViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        ImageView categoryImage;
        FloatingActionButton categoryActionFAB;
        CardView itemContainer;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name_tvw);
            categoryImage = itemView.findViewById(R.id.category_imgvw);
            categoryActionFAB = itemView.findViewById(R.id.category_action);
            itemContainer = itemView.findViewById(R.id.category_container);
        }
    }
}
