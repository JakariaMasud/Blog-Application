package com.example.blogapplication.repositories;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.blogapplication.BlogApi;
import com.example.blogapplication.Constants;
import com.example.blogapplication.SingleLiveEvent;
import com.example.blogapplication.database.BlogDao;
import com.example.blogapplication.models.Blog;
import com.example.blogapplication.models.Blogs;
import com.example.blogapplication.models.Result;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class BlogRepository {
    BlogApi blogApi;
    BlogDao blogDao;
    SharedPreferences preferences;

    SingleLiveEvent<Result>updateProcessLiveData=new SingleLiveEvent<>();
    MutableLiveData<Blog>getBlog=new MutableLiveData<>();


    @Inject
    public BlogRepository(BlogApi blogApi, BlogDao blogDao, SharedPreferences preferences) {
        this.blogApi=blogApi;
        this.blogDao=blogDao;
        this.preferences=preferences;
        init();
    }

    public LiveData<List<Blog>> getBlogList() {

        return blogDao.getAllBlog();
    }
    public LiveData<Blog>getBlog(){
        return getBlog;
    }

    private void init() {
        boolean hasData = preferences.contains(Constants.DATA_LOAD_KEY);
        if(!hasData){
            blogApi.getBlogs().enqueue(new Callback<Blogs>() {
                @Override
                public void onResponse(Call<Blogs> call, Response<Blogs> response) {
                    if(response.isSuccessful()){
                        for(Blog singleBlog:response.body().blogs){
                            insertBlog(singleBlog);
                        }
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putBoolean(Constants.DATA_LOAD_KEY,true);
                        editor.apply();


                    }
                }

                @Override
                public void onFailure(Call<Blogs> call, Throwable t) {

                }
            });
        }

    }


    public void insertBlog (Blog blog){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                blogDao.insertBlog(blog);
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("info","item inserted");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void updateBlog(Blog blog){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                blogDao.UpdateBlog(blog);
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("info","item updated");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getBlogById(int id){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
            Blog blog=blogDao.getBlogById(id);
            getBlog.postValue(blog);
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
