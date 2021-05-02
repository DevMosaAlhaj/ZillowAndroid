package com.mosaalhaj.zillow.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mosaalhaj.zillow.ui.view.fragment.HomeFragment;

public class HomePagerAdapter extends FragmentStateAdapter {


    public HomePagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
