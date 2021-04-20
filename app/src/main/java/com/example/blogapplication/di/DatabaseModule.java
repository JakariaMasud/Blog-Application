package com.example.blogapplication.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.blogapplication.database.BlogDao;
import com.example.blogapplication.database.BlogDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import dagger.hilt.components.SingletonComponent;

import static android.content.Context.MODE_PRIVATE;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    BlogDatabase provideBlogDatabase( @ApplicationContext Context application) {
        return Room.databaseBuilder(application, BlogDatabase.class, "blog_database")
                .fallbackToDestructiveMigration()
                .build();

    }

    @Provides
    @Singleton
    BlogDao provideBlogDao(BlogDatabase blogDatabase){
        return blogDatabase.blogDao();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("MyPref", MODE_PRIVATE);
    }



}
