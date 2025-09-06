package com.shivamsingh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shivamsingh.verinews.BuildConfig;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shivamsingh.verinews.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HealthFragment extends Fragment {

    static final String API_KEY=BuildConfig.NEWS_API_KEY;
    RecyclerView recyclerView;
    Adapter adapter;

    ArrayList<Model> arrayList;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);

        recyclerView=view.findViewById(R.id.health_recyclerView);
        progressBar=view.findViewById(R.id.progressBar);

        getNews();
        return  view;
    }
    void getNews(){
        progressBar.setVisibility(View.VISIBLE);
        ApiUtilities.getApiInterface().getCategory("us", "health","100", API_KEY).enqueue(new Callback<NewsArticle>() {
            @Override
            public void onResponse(Call<NewsArticle> call, Response<NewsArticle> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getArticles() != null) {
                    Log.d("NEWS", "Articles fetched: " + response.body().getArticles().size());
                    Log.d("NEWS_RESPONSE", "Total: " + response.body().getTotalResults());
                    Log.d("NEWS_ARTICLES", new Gson().toJson(response.body().getArticles()));

                    arrayList=new ArrayList<>(response.body().getArticles());
                    adapter=new Adapter(getContext(),arrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("NEWS", "Response failed or empty body");
                    Toast.makeText(getContext(), "No news available", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<NewsArticle> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("NEWS", "API call failed: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to load news", Toast.LENGTH_SHORT).show();

            }
        });
    }
}