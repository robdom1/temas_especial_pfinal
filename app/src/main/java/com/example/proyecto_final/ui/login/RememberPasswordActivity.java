package com.example.proyecto_final.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.proyecto_final.databinding.ActivityRememberPasswordBinding;

public class RememberPasswordActivity extends AppCompatActivity {
    private Intent nextActivity;
    private ActivityRememberPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRememberPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rememberRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity = new Intent(RememberPasswordActivity.this, RegisterActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });

        binding.rememberLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity = new Intent(RememberPasswordActivity.this, LoginActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });
    }
}