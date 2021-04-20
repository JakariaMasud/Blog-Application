package com.example.blogapplication.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blogapplication.R;
import com.example.blogapplication.databinding.FragmentBlogDetailBinding;
import com.example.blogapplication.databinding.FragmentHomeBinding;
import com.example.blogapplication.models.Blog;
import com.example.blogapplication.viewModels.BlogViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class BlogDetailFragment extends Fragment {
    FragmentBlogDetailBinding binding;
    BlogViewModel blogViewModel;
    int  id;
    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentBlogDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       int itemId= BlogDetailFragmentArgs.fromBundle(getArguments()).getItemId();
        navController= Navigation.findNavController(view);
        blogViewModel = new ViewModelProvider(requireActivity()).get(BlogViewModel.class);
        blogViewModel.getBlogById(itemId);
        blogViewModel.getBlog().observe(getViewLifecycleOwner(), new Observer<Blog>() {
            @Override
            public void onChanged(Blog blog) {
                id=blog.getId();
                binding.blogTitleTV.setText(blog.getTitle());
                Picasso.get().load(blog.getCover_photo()).into(binding.blogCoverIV);
                binding.blogDesTV.setText(blog.getDescription());
                binding.blogCategoryTV.setText("Category : "+ConstructStringFromList(blog.getCategories()));
                Picasso.get().load(blog.getAuthor().getAvatar()).into(binding.authorIV);
                binding.authorNameTV.setText(blog.getAuthor().getName());
                binding.authorProfessionTV.setText(blog.getAuthor().getProfession());

            }
        });

        binding.editFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action=BlogDetailFragmentDirections.actionBlogDetailFragmentToAddUpdateFragment(id);
                navController.navigate(action);
            }
        });
    }

    private String ConstructStringFromList(List<String> stringList){
      StringBuilder stringBuilder=new StringBuilder();
      for(int i=0;i<stringList.size();i++){
          stringBuilder.append(stringList.get(i));
          if(i!=stringList.size()-1){
              stringBuilder.append(" , ");
          }
      }
      return stringBuilder.toString();
    }
}