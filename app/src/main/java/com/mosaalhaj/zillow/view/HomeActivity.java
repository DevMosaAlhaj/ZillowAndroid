package com.mosaalhaj.zillow.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.adapter.PostAdapter;
import com.mosaalhaj.zillow.databinding.ActivityHomeBinding;
import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.viewmodel.HomeViewModel;
import com.mosaalhaj.zillow.viewmodel.LoginViewModel;

import java.util.ArrayList;

import static com.mosaalhaj.zillow.item.Constants.ACCESS_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.NOT_FOUND;
import static com.mosaalhaj.zillow.item.Constants.REFRESH_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.SHARED_PREFERENCE_FILE;
import static com.mosaalhaj.zillow.item.Constants.UN_AUTHORIZED;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Post> posts;
    private PostAdapter adapter;
    private SharedPreferences preferences;
    private HomeViewModel homeViewModel;
    private int pageNumber ;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posts = new ArrayList<>();
        adapter = new PostAdapter(getBaseContext(), posts);
        pageNumber = 1;
        preferences = getSharedPreferences(SHARED_PREFERENCE_FILE, MODE_PRIVATE);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        ActivityHomeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.setLifecycleOwner(this);


        binding.homeRvPosts.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getBaseContext(), 1);

        binding.homeRvPosts.setLayoutManager(manager);
        binding.homeBar.setSelectedItemId(R.id.home_nav_bar_home);

        getPosts();

        homeViewModel.liveData.observe(this, response -> {
            if (response.isSucceeded()) {

                if (response.getData().getPagesCount()>pageNumber){
                    posts.addAll(response.getData().getData());
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getBaseContext(), "Got Empty Data From Server", Toast.LENGTH_SHORT).show();

            } else if (response.getMessage().equals(UN_AUTHORIZED)) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(ACCESS_TOKEN);
                getPosts();
                Toast.makeText(getBaseContext(), "Loading ...", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getBaseContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    private void getPosts() {

        String refreshToken = preferences.getString(REFRESH_TOKEN, NOT_FOUND);



        if (!refreshToken.equals(NOT_FOUND)) {


            LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

            viewModel.liveData.observe(this, response -> {
                if (response.getData() != null)
                    homeViewModel.getPosts(pageNumber, "Bearer "+response.getData().getTokenResponse().getToken());
                else {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            viewModel.refresh(refreshToken);

        }

    }

}