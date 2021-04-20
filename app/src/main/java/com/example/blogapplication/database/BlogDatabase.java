package com.example.blogapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.blogapplication.conveters.CategoryConverter;
import com.example.blogapplication.models.Blog;

@Database(entities = {Blog.class}, version = 1)
@TypeConverters({CategoryConverter.class})
public abstract class BlogDatabase extends RoomDatabase {
    public abstract BlogDao blogDao();
}