package com.example.blogapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blogapplication.models.Blog;

import java.util.List;

@Dao
public interface BlogDao {
    @Query("SELECT * FROM blog_table")
    LiveData<List<Blog>> getAllBlog();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBlog(Blog blog);

    @Update
    void UpdateBlog(Blog blog);

    @Query("SELECT * FROM blog_table WHERE id =:id")
    Blog getBlogById(int id);

}
