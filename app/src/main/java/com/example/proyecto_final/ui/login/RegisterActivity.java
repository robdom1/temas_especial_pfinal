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
import com.example.proyecto_final.databinding.ActivityRegisterBinding;
import com.example.proyecto_final.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Intent nextActivity;
    private ActivityRegisterBinding binding;
    private UserViewmodel userViewmodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });

        binding.registerForgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity = new Intent(RegisterActivity.this, RememberPasswordActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.registerNameInput.getText().toString();
                if(name.equals("")){
                    binding.registerNameInput.setError("This field can't be empty");
                    return;
                }

                String username = binding.registerUsernameInput.getText().toString();
                if(username.equals("")){
                    binding.registerUsernameInput.setError("This field can't be empty");
                    return;
                }

                String userEmail = binding.registerEmailInput.getText().toString();
                if (userEmail.equals("")){
                    binding.registerEmailInput.setError("This field can't be empty");
                    return;
                }
                if(!isEmailValid(userEmail)){
                    binding.registerEmailInput.setError("This email is not valid");
                    return;
                }

                String password = binding.registerPasswordInput.getText().toString();
                if(password.equals("")){
                    binding.registerPasswordInput.setError("This field can't be empty");
                    return;
                }

                String confirmPassword = binding.registerPasswordConfirmInput.getText().toString();
                if(confirmPassword.equals("")){
                    binding.registerPasswordConfirmInput.setError("This field can't be empty");
                    return;
                }
                if (!confirmPassword.equals(password)){
                    binding.registerPasswordConfirmInput.setError("Password and confirmation are not equal");
                    return;
                }

                String phoneNumber = binding.registerPhoneInput.getText().toString();
                if(phoneNumber.equals("")){
                    binding.registerPhoneInput.setError("This field can't be empty");
                    return;
                }

                User newUser = new User(userEmail, name, username, phoneNumber, password);
                userViewmodel = new ViewModelProvider(RegisterActivity.this).get(UserViewmodel.class);
                userViewmodel.insert(newUser);

                SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("email",userEmail );
                Ed.putString("Psw",password);
                Ed.commit();


                nextActivity = new Intent(RegisterActivity.this, MainActivity.class);
                nextActivity.putExtra("loggedUser", userEmail);
                startActivity(nextActivity);
                finish();

//                authenticate(userEmail, password);


            }
        });


    }

    private void authenticate(String userEmail, String userPass){

        userViewmodel.getUser(userEmail).observe(RegisterActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d("auth", "onClick: "+ user.getEmail() + " - " + user.getPassword() + " - " + userPass);
                Log.d("auth", "onClick: "+ Objects.equals(user.getPassword(), userPass));
                if( Objects.equals(user.getPassword(), userPass)){
                    userViewmodel.setCurrentUser(user);
                    nextActivity = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(nextActivity);
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}