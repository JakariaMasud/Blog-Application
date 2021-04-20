package com.example.blogapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapplication.OnItemClickListener;
import com.example.blogapplication.databinding.SingleBlogBinding;
import com.example.blogapplication.models.Blog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
    List<Blog> blogList;
    OnItemClickListener listener;

    public BlogAdapter(List<Blog> blogList,OnItemClickListener listener) {
        this.blogList = blogList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleBlogBinding binding=SingleBlogBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BlogViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        Blog blog=blogList.get(position);
        holder.blogBinding.titleTV.setText(blog.getTitle());
        Picasso.get().load(blog.getCover_photo()).into(holder.blogBinding.posterIV);

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    class BlogViewHolder extends RecyclerView.ViewHolder{
        SingleBlogBinding blogBinding;

        public BlogViewHolder(@NonNull SingleBlogBinding blogBinding) {
            super(blogBinding.getRoot());
            this.blogBinding=blogBinding;
            this.blogBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(blogList.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
