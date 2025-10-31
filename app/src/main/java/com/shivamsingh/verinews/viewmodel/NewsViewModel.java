package com.shivamsingh.verinews.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shivamsingh.verinews.model.Model;
import com.shivamsingh.verinews.model.NewsArticle;
import com.shivamsingh.verinews.network.ApiUtilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    final private MutableLiveData<ArrayList<Model>> newsList = new MutableLiveData<>();
    final private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    final private MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Model>> getNewsListObserver() {
        return newsList;
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void makeApicall(String country, String pageSize, String apiKey, String category) {
        isLoading.postValue(true);

        Call<NewsArticle> call;

        if (category == null || category.isEmpty()) {
            // General news
            call = ApiUtilities.getApiInterface().getNews(country, pageSize, apiKey);
        } else {
            // Category-specific news
            call = ApiUtilities.getApiInterface().getCategory(country, category, pageSize, apiKey);
        }

        call.enqueue(new Callback<NewsArticle>() {
            @Override
            public void onResponse(@NonNull Call<NewsArticle> call, @NonNull Response<NewsArticle> response) {
                isLoading.postValue(false);

                if (response.isSuccessful() && response.body() != null && response.body().getArticles() != null) {
                    newsList.postValue(response.body().getArticles());
                } else {
                    newsList.postValue(null);
                    toastMessage.postValue("No news available");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsArticle> call, @NonNull Throwable t) {
                isLoading.postValue(false);
                newsList.postValue(null);
                toastMessage.postValue("Failed to load news: " + t.getMessage());
            }
        });
    }

}