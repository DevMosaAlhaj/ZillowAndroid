package com.mosaalhaj.zillow.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.adapter.PostAdapter;
import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.ui.view.activity.LoginActivity;
import com.mosaalhaj.zillow.ui.viewmodel.HomeViewModel;
import com.mosaalhaj.zillow.ui.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.mosaalhaj.zillow.item.Constants.NOT_FOUND;
import static com.mosaalhaj.zillow.item.Constants.REFRESH_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.SHARED_PREFERENCE_FILE;
import static com.mosaalhaj.zillow.item.Constants.UN_AUTHORIZED;


public class PostFragment extends Fragment {


    private ArrayList<Post> posts;
    private PostAdapter adapter;
    private SharedPreferences preferences;
    private HomeViewModel homeViewModel;
    private int pageNumber;
    private RecyclerView rv_postsList;

    public PostFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        posts = new ArrayList<>();
        adapter = new PostAdapter(getContext(), posts);
        pageNumber = 1;
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFERENCE_FILE, MODE_PRIVATE);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_post, null);

        rv_postsList = view.findViewById(R.id.fragment_post_rv_posts);
        return view;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        rv_postsList.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);

        rv_postsList.setLayoutManager(manager);

        getPosts();

        homeViewModel.liveData.observe(this, response -> {
            if (response.isSucceeded()) {

                if (response.getData().getPagesCount() >= pageNumber) {

                    posts.addAll(response.getData().getData());
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getContext(), "Got Empty Data From Server", Toast.LENGTH_SHORT).show();

            } else if (response.getMessage().equals(UN_AUTHORIZED)) {

                getPosts();
                Toast.makeText(getContext(), "Loading ...", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        });


    }

    private void getPosts() {

        String refreshToken = preferences.getString(REFRESH_TOKEN, NOT_FOUND);

        if (!refreshToken.equals(NOT_FOUND)) {


            LoginViewModel viewModel =
                    new ViewModelProvider(this).get(LoginViewModel.class);

            viewModel.liveData.observe(this, response -> {
                if (response.getData() != null)
                    homeViewModel.getPosts(pageNumber, "Bearer " + response.getData().getTokenResponse().getToken());
                else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

            viewModel.refresh(refreshToken);

        }

    }

}