package com.example.proyecto_final.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.proyecto_final.MainActivity;
import com.example.proyecto_final.R;
import com.example.proyecto_final.entities.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

//        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String email=sp1.getString("email", null);
                String pass = sp1.getString("Psw", null);

                if(email != null && pass != null){

                    UserViewmodel userViewmodel = new ViewModelProvider(SplashActivity.this).get(UserViewmodel.class);

                    userViewmodel.getUser(email).observe(SplashActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            Intent intent;
                            if(user == null){
                                intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }

                            if( Objects.equals(user.getPassword(), pass)){
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                                intent.putExtra("loggedUser", email);
                            } else {
                                intent = new Intent(SplashActivity.this, LoginActivity.class);
                            }
                            startActivity(intent);
                            finish();

                        }
                    });
                }else{
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, 2000);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        Intent i;
//        if (currentUser != null){
//            i = new Intent(SplashActivity.this, MainActivity.class);
//        }else{
//            i = new Intent(SplashActivity.this, LoginActivity.class);
//        }
//        startActivity(i);
//        finish();
//    }
}