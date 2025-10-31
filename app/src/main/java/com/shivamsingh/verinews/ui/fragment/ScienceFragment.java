package com.shivamsingh.verinews.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shivamsingh.verinews.BuildConfig;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shivamsingh.verinews.adapter.Adapter;
import com.shivamsingh.verinews.databinding.FragmentScienceBinding;
import com.shivamsingh.verinews.model.Model;
import com.shivamsingh.verinews.viewmodel.NewsViewModel;

import java.util.ArrayList;

public class ScienceFragment extends Fragment {

    static final String API_KEY = BuildConfig.NEWS_API_KEY;
    Adapter adapter;
    ArrayList<Model> arrayList;
    NewsViewModel newsViewModel;
    FragmentScienceBinding binding;
    private boolean isFirstLoad = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentScienceBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), arrayList);
        binding.scienceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.scienceRecyclerView.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Make API call
                newsViewModel.makeApicall("us", "100", API_KEY, "science");
            }
        });

        initViewModel();
        return binding.getRoot();
    }

    private void initViewModel() {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        // Observe news data
        newsViewModel.getNewsListObserver().observe(getViewLifecycleOwner(), new Observer<ArrayList<Model>>() {
            @Override
            public void onChanged(ArrayList<Model> models) {
                if (models != null) {
                    arrayList.clear();
                    arrayList.addAll(models);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        // Observe loading state
        newsViewModel.getLoadingState().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    if (isFirstLoad) {
                        // Show center progress bar only on first load
                        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    }

                    // Stop swipe refresh spinner when done
                    if (!isLoading) {
                        binding.swipeRefresh.setRefreshing(false);
                        isFirstLoad = false; // After first load, stop using the big ProgressBar
                    }
                }
            }
        });

        // Observe toast messages
        newsViewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Make API call
        newsViewModel.makeApicall("us", "100", API_KEY, "science");
    }
}
