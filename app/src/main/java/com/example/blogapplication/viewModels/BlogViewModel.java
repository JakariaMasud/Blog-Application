package com.example.blogapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.blogapplication.models.Blog;
import com.example.blogapplication.repositories.BlogRepository;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BlogViewModel extends ViewModel {
    BlogRepository repository;

    @Inject
    public BlogViewModel(BlogRepository blogRepo){
            repository=blogRepo;
    }
    public LiveData<List<Blog>>getBlogList(){
        return repository.getBlogList();
    }
    public LiveData<Blog>getBlog(){
        return repository.getBlog();
    }
    public void insertBlog(Blog blog){
        repository.insertBlog(blog);
    }
    public void updateBlog(Blog blog){
        repository.updateBlog(blog);
    }
    public void getBlogById(int id){
        repository.getBlogById(id);
    }


}
