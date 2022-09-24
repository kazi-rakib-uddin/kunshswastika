package com.example.kunshswastika.ActivityEcommers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.kunshswastika.Activity.MainActivity;
import com.example.kunshswastika.Fragment.CategoryFragment;
import com.example.kunshswastika.Fragment.HomeFragment;
import com.example.kunshswastika.R;
import com.example.kunshswastika.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        initView();
    }


    private void initView() {

        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,
                new HomeFragment()).commit();

        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_category:
                        selectedFragment = new CategoryFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,
                        selectedFragment).commit();


                return true;
            }
        });
        //binding.bottomNav.setSelectedItemId(R.id.nav_home);


    }


}