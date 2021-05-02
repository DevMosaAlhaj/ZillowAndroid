package com.mosaalhaj.zillow.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.ui.view.fragment.PostFragment;

import java.util.ArrayList;

public class PostPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Post> posts;

    public PostPagerAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<Post> posts) {
        super(fragmentActivity);
        this.posts = posts;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new PostFragment(posts.get(position));
    }

    @Override
    public int getItemCount() {
        if (posts==null || posts.isEmpty())
            return 0;

        return posts.size();
    }
}
