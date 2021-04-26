package com.mosaalhaj.zillow.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.adapter.HomePagerAdapter;
import com.mosaalhaj.zillow.adapter.PostAdapter;
import com.mosaalhaj.zillow.databinding.ActivityHomeBinding;
import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.ui.viewmodel.HomeViewModel;
import com.mosaalhaj.zillow.ui.viewmodel.LoginViewModel;

import java.util.ArrayList;

import static com.mosaalhaj.zillow.item.Constants.ACCESS_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.NOT_FOUND;
import static com.mosaalhaj.zillow.item.Constants.REFRESH_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.SHARED_PREFERENCE_FILE;
import static com.mosaalhaj.zillow.item.Constants.UN_AUTHORIZED;

public class HomeActivity extends AppCompatActivity {


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

    private void clearStack() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
        }

        if (fragmentManager.getFragments().size() > 0) {
            for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
                Fragment mFragment = fragmentManager.getFragments().get(i);
                if (mFragment != null) {
                    fragmentManager.beginTransaction().remove(mFragment).commit();
                }
            }
        }
    }

}