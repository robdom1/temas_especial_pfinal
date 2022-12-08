package com.example.proyecto_final.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.proyecto_final.MainActivity;
import com.example.proyecto_final.databinding.ActivityLoginBinding;
import com.example.proyecto_final.entities.User;
import com.example.proyecto_final.ui.product.ProductsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Intent nextActivity;
    private ActivityLoginBinding binding;
    private UserViewmodel userViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });

        binding.loginForgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity = new Intent(LoginActivity.this, RememberPasswordActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = binding.loginEmailInput.getText().toString();
                if(userEmail.equals("")){
                    binding.loginEmailInput.setError("Empty Field");
                    return;
                }

                String password = binding.loginPasswordInput.getText().toString();
                if(password.equals("")){
                    binding.loginPasswordInput.setError("Empty Field");
                    return;
                }

                userViewmodel = new ViewModelProvider(LoginActivity.this).get(UserViewmodel.class);

                authenticate(userEmail, password);

//                if(userViewmodel.authenticate(userEmail, password)){
//                    nextActivity = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(nextActivity);
//                    finish();
//                }else{
//                    Toast.makeText(LoginActivity.this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
//                }

            }
        });


    }

    private void authenticate(String userEmail, String userPass){

        userViewmodel.getUser(userEmail).observe(LoginActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if(user == null){
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    binding.loginEmailInput.setText("");
                    binding.loginPasswordInput.setText("");
                    binding.loginEmailInput.requestFocus();
                    return;
                }
                Log.d("auth", "onClick: "+ user.getEmail() + " - " + user.getPassword() + " - " + userPass);
                Log.d("auth", "onClick: "+ Objects.equals(user.getPassword(), userPass));
                if( Objects.equals(user.getPassword(), userPass)){
//                    userViewmodel.setCurrentUser(user);

                    SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("email",userEmail );
                    Ed.putString("Psw",userPass);
                    Ed.commit();


                    nextActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(nextActivity);
                    nextActivity.putExtra("loggedUser", userEmail);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    binding.loginEmailInput.setText("");
                    binding.loginPasswordInput.setText("");
                    binding.loginEmailInput.requestFocus();
                }
            }
        });
    }
}