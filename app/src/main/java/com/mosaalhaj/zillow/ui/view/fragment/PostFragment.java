package com.mosaalhaj.zillow.ui.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.model.Post;

import java.util.ArrayList;


public class PostFragment extends Fragment {

    private Post post;
    private TextView tv_title;

    public PostFragment() {
    }

    public PostFragment(Post posts){
        this.post = posts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}