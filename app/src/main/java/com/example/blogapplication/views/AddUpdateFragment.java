package com.example.blogapplication.views;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blogapplication.Constants;
import com.example.blogapplication.R;
import com.example.blogapplication.databinding.FragmentAddUpdateBinding;
import com.example.blogapplication.models.Author;
import com.example.blogapplication.models.Blog;
import com.example.blogapplication.models.Result;
import com.example.blogapplication.viewModels.BlogViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AddUpdateFragment extends Fragment {
    FragmentAddUpdateBinding binding;
    boolean [] selectedCategory;
    Set<Integer> categorySet=new LinkedHashSet<>();
    String[] category={"Business","Entertainment","Productivity","Music","Cooking","Sports","Fitness","Lifestyle"};
    List<String>selectedList=new ArrayList<>();
    BlogViewModel blogViewModel;
    Blog selectedBlog;
    NavController navController;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddUpdateBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        blogViewModel = new ViewModelProvider(requireActivity()).get(BlogViewModel.class);
        navController= Navigation.findNavController(view);
        selectedCategory=new boolean[category.length];
        int id=AddUpdateFragmentArgs.fromBundle(getArguments()).getItemId();
        if(id==0){
            //Add blog event
            binding.addUpdateBTN.setText(Constants.ADD_BUTTON_TEXT);
            settingUpMultiSelectDialog();
            settingUpListener();

        }else{
            //update blog event
            blogViewModel.getBlogById(id);
            binding.addUpdateBTN.setText(Constants.UPDATE_BUTTON_TEXT);
            blogViewModel.getBlog().observe(getViewLifecycleOwner(), new Observer<Blog>() {
                @Override
                public void onChanged(Blog blog) {
                    if(blog!=null){
                        selectedBlog=blog;
                        settingUpSelectedList();
                        settingUpMultiSelectDialog();
                        settingUpListener();
                        binding.titleET.setText(blog.getTitle());
                        binding.blogdesET.setText(blog.getDescription());
                        selectedList.clear();
                        selectedList.addAll(blog.getCategories());
                        StringBuilder stringBuilder=new StringBuilder();
                        for(int i=0;i<selectedList.size();i++){
                            stringBuilder.append(selectedList.get(i));
                            if(i!=selectedList.size()-1){
                                stringBuilder.append(" , ");
                            }
                        }
                        binding.categoryTV.setText(stringBuilder.toString());
                    }
                }
            });
        }


    }

    private void settingUpSelectedList() {
        for(int i=0;i<category.length;i++){
            for(int j=0;j<selectedBlog.getCategories().size();j++){
                if(category[i].equals(selectedBlog.getCategories().get(j))){
                    selectedCategory[i]=true;
                    categorySet.add(i);

                }
            }
        }
    }

    private void settingUpListener() {
        blogViewModel.insertProcessLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
              if(result!=null){
                  if(result==Result.Success){
                      Toast.makeText(getContext(), "Blog is added Successfully", Toast.LENGTH_SHORT).show();
                      navController.navigate(R.id.action_addUpdateFragment_to_homeFragment);
                  }else{
                      Toast.makeText(getContext(), "Failed in inserting Blog", Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });
        blogViewModel.updateProcessLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result!=null){
                    if(result==Result.Success){
                        Toast.makeText(getContext(), "Blog is Updated Successfully", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_addUpdateFragment_to_homeFragment);
                    }else{
                        Toast.makeText(getContext(), "Failed in updating Blog", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        binding.addUpdateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isEveryFieldValid =checkAllField();
               if(isEveryFieldValid){
                   String title=binding.titleET.getText().toString();
                   String description=binding.blogdesET.getText().toString();
                   Author author=new Author(1,"John Doe","https://i.pravatar.cc/250","Content Writer");
                   String buttonText= binding.addUpdateBTN.getText().toString();
                   if(buttonText.equals(Constants.ADD_BUTTON_TEXT)){
                      //add blog
                       blogViewModel.insertBlog(new Blog(0,title,description,Constants.BLOG_COVER1,selectedList,author));


                   }else{
                     //update
                       blogViewModel.updateBlog(new Blog(selectedBlog.getId(),title,description,selectedBlog.getCover_photo(),selectedList,author));

                   }
               }
               }

        });
    }

    private boolean checkAllField() {
        String title=binding.titleET.getText().toString();
        String description=binding.blogdesET.getText().toString();
        if(TextUtils.isEmpty(title)){
            binding.titleET.setError("Title Field Can not be empty");
            return false;
        }else{
            if(TextUtils.isEmpty(description)){
                binding.blogdesET.setError("Description Can not be empty");
                return false;
            }else{
                if(selectedList.isEmpty()){
                    Toast.makeText(getContext(), "You Must select category", Toast.LENGTH_SHORT).show();
                    return  false;
                }
            }
        }
        return true;
    }




    private void settingUpMultiSelectDialog() {
        binding.categoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Select Category");
                builder.setMultiChoiceItems(category, selectedCategory, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            categorySet.add(which);

                        }else{
                            categorySet.remove(which);
                        }
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder=new StringBuilder();
                        selectedList.clear();
                       for(Integer i:categorySet){
                           selectedList.add(category[i]);
                           //no built in way for getting the last item of set
                           // as i want to concat a comma after each item except the last one
                           //so it needs another loop to construct the string for textview

                      }
                       for(int i=0;i<selectedList.size();i++){
                           stringBuilder.append(selectedList.get(i));
                           if(i!=selectedList.size()-1){
                               stringBuilder.append(" , ") ;
                           }
                       }
                        binding.categoryTV.setText(stringBuilder.toString());
                    }
                });
                builder.show();
            }
        });
    }


}