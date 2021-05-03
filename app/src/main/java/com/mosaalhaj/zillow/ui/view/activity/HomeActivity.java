package com.mosaalhaj.zillow.ui.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.adapter.HomePagerAdapter;
import com.mosaalhaj.zillow.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ActivityHomeBinding binding
                =  DataBindingUtil.setContentView(HomeActivity.this,R.layout.activity_home);

        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());

        binding.homeViewPager.setAdapter(adapter);



        binding.homeViewPager.setCurrentItem(0);

        binding.homeBar.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home_nav_bar_home:
                    binding.homeViewPager.setCurrentItem(0);
                    break;
                case R.id.home_nav_bar_bookmark:
                    binding.homeViewPager.setCurrentItem(1);
                    break;
                case R.id.home_nav_bar_settings:
                    binding.homeViewPager.setCurrentItem(2);
                    break;
            }

            return true;
        });



    }



}