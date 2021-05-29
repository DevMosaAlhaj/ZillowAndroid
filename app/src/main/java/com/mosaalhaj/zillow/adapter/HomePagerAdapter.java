package com.mosaalhaj.zillow.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mosaalhaj.zillow.ui.view.fragment.HomeFragment;
import com.mosaalhaj.zillow.ui.view.fragment.PostFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {


    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
