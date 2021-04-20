package com.example.blogapplication;
import com.example.blogapplication.models.Blogs;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface BlogApi {

    @GET("sohel-cse/simple-blog-api/db")
    Call<Blogs> getBlogs();
}
