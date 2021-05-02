package com.mosaalhaj.zillow.ui.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.adapter.PostPagerAdapter;
import com.mosaalhaj.zillow.item.ZoomOutPageTransformer;
import com.mosaalhaj.zillow.model.Image;
import com.mosaalhaj.zillow.model.Post;

import java.util.ArrayList;

import static com.mosaalhaj.zillow.item.Constants.CURRENT_POST;
import static com.mosaalhaj.zillow.item.Constants.POST_ARRAY;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ViewPager2 pager = findViewById(R.id.post_view_pager);

        ArrayList<Post> posts = getIntent()
                .getParcelableArrayListExtra(POST_ARRAY);
        int position = getIntent().getIntExtra(CURRENT_POST,0);

        PostPagerAdapter adapter = new PostPagerAdapter(this,posts);
        pager.setPageTransformer(new ZoomOutPageTransformer());
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);

    }

}