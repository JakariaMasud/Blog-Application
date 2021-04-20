package com.example.blogapplication.di;

import android.content.SharedPreferences;

import com.example.blogapplication.BlogApi;
import com.example.blogapplication.database.BlogDao;
import com.example.blogapplication.repositories.BlogRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    BlogRepository provideBlogRepo(BlogApi blogApi, BlogDao blogDao, SharedPreferences preferences){
        return new BlogRepository(blogApi,blogDao,preferences);
    }

}
