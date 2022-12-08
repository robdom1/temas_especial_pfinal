package com.example.proyecto_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.proyecto_final.entities.User;
import com.example.proyecto_final.ui.login.LoginActivity;
import com.example.proyecto_final.ui.login.UserViewmodel;
import com.example.proyecto_final.ui.product.ProductsFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration =
                new AppBarConfiguration
                        .Builder(R.id.nav_home, R.id.nav_category, R.id.nav_profile, R.id.nav_product, R.id.nav_help)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);




        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

//        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_name);
        TextView navUserEmail = (TextView) headerView.findViewById(R.id.nav_header_email);

        String currentUserEmail = getIntent().getStringExtra("loggedUser");

        UserViewmodel userViewmodel = new ViewModelProvider(MainActivity.this).get(UserViewmodel.class);

        userViewmodel.getUser(currentUserEmail).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    userViewmodel.setCurrentUser(user);
                    navUsername.setText(user.getUserName());
                    navUserEmail.setText(user.getEmail());
                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);



        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.action_search);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();


//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initSearch();
//            }
//        });

        // below line is to call set on query text listener method.
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // inside on query text change method we are
//                // calling a method to filter our recycler view.
////                filter(newText);
//                return false;
//            }
//        });

        MenuItem cartItem = menu.findItem(R.id.action_cart);

        cartItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                navController.navigate(R.id.nav_cart);
                return false;
            }
        });

//        Button cartBtn = (Button) cartItem.getActionView();
//
//        cartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    Toast.makeText(MainActivity.this, "Cart pressed",
//                            Toast.LENGTH_SHORT).show();
//
//            }
//        });

        // getting search view of our item.
//        SearchView searchView = (SearchView) searchItem.getActionView();


        return true;

    }

    private void initSearch() {


        ProductsFragment fragment = new ProductsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back.

        transaction.replace(R.id.nav_host_fragment_content_main, fragment);
        transaction.addToBackStack(null); // Because we have only one fragment that why we haven't added this line. In case you have multiple fragment include this line.

        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void goToLogin(){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void cleanSharedPreference(){
        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("email",null );
        Ed.putString("Psw",null);
        Ed.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            cleanSharedPreference();
                            goToLogin();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            drawer.closeDrawer(navigationView);
        }

        boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
        if (handled) {
            ViewParent parent = navigationView.getParent();
            if (parent instanceof DrawerLayout) {
                ((DrawerLayout) parent).closeDrawer(navigationView);
            }
        }

        return handled;

    }
}