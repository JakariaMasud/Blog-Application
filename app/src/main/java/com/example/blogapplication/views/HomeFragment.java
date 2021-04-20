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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.blogapplication.OnItemClickListener;
import com.example.blogapplication.adapter.BlogAdapter;
import com.example.blogapplication.databinding.FragmentHomeBinding;
import com.example.blogapplication.models.Blog;
import com.example.blogapplication.viewModels.BlogViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements OnItemClickListener {
    FragmentHomeBinding binding;
    RecyclerView.LayoutManager layoutManager;;
    BlogAdapter adapter;
    BlogViewModel blogViewModel;
    NavController navController;
    List<Blog>blogList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        layoutManager= new LinearLayoutManager(getActivity());
        binding.blogRV.setLayoutManager(layoutManager);
        binding.blogRV.setHasFixedSize(true);
        blogViewModel = new ViewModelProvider(requireActivity()).get(BlogViewModel.class);
        blogViewModel.getBlogList().observe(getViewLifecycleOwner(), new Observer<List<Blog>>() {
            @Override
            public void onChanged(List<Blog> blogs) {
                if(!blogs.isEmpty()){
                    blogList=blogs;
                    adapter=new BlogAdapter(blogs,HomeFragment.this::onItemClick);
                    binding.blogRV.setAdapter(adapter);
                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action=HomeFragmentDirections.actionHomeFragmentToAddUpdateFragment(0);
                navController.navigate(action);

            }
        });


    }

    @Override
    public void onItemClick(int itemId) {
        NavDirections action=HomeFragmentDirections.actionHomeFragmentToBlogDetailFragment(itemId);
        navController.navigate(action);

    }
}