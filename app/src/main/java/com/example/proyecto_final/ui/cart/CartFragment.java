package com.example.proyecto_final.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_final.databinding.FragmentCartBinding;
import com.example.proyecto_final.entities.relations.CartProductWithProducts;
import com.example.proyecto_final.entities.relations.UserCart;
import com.example.proyecto_final.ui.login.UserViewmodel;

import java.text.DecimalFormat;


public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private UserViewmodel mViewModel;
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewModel = new ViewModelProvider(this).get(UserViewmodel.class);
        binding = FragmentCartBinding.inflate(inflater, container, false);

        adapter = new CartAdapter(this);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        SharedPreferences sp1= getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        String email=sp1.getString("email", null);

        mViewModel.getUserCart(email).observe(getViewLifecycleOwner(), new Observer<UserCart>() {
            @Override
            public void onChanged(UserCart userCart) {
                adapter.setCartList(userCart.getCartProducts());
                Double total = 0.0;
                for(CartProductWithProducts cartProduct: userCart.getCartProducts()){
                    int qty = cartProduct.getCartProduct().getProductQty();
                    Double price = cartProduct.getProducts().get(0).getPrice();
                    total += price * qty;
                }
                DecimalFormat df = new DecimalFormat("0.00");
                df.setMaximumFractionDigits(2);
                String precioStr = "$" + df.format(total);
                binding.cartTotalTextView.setText("Total price " + precioStr);
            }
        });



        return binding.getRoot();
    }
}