package com.mosaalhaj.zillow.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.databinding.HomeListItemBinding;
import com.mosaalhaj.zillow.model.Post;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import static com.mosaalhaj.zillow.item.Constants.API_URL;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final Context context;
    private final ArrayList<Post> posts;
    private final ImageListener imageListener ;
    private Post post ;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
        imageListener = (position, imageView) -> {
            if (post != null && post.getImages() !=null && !post.getImages().isEmpty()){
                String imageUrl = API_URL+post.getImages().get(position).getUrl();
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.post_image)
                        .centerCrop().into(imageView);
            }
        };
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_list_item, null);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        post = posts.get(position);

        holder.binding.homeListItemTvName.setText(post.getName());

        String address = post.getAddress().getCountryName()+","+post.getAddress().getCityName();

        holder.binding.homeListItemTvAddress.setText(address);
        holder.binding.homeListItemCvPostPhotos.setPageCount(post.getImages().size());
        holder.binding.homeListItemCvPostPhotos.setImageListener(imageListener);

    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        else
            return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        public HomeListItemBinding binding;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
