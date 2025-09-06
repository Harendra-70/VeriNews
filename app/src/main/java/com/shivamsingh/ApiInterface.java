package com.shivamsingh;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // Base URL for the News API
    // All requests will be relative to this URL
     String BASE_URL = "https://newsapi.org/v2/";

    /**
     * Get top headlines for a specified country.
     *
     * @param country  The 2-letter country code (e.g., "us" for United States)
     * @param pageSize Number of articles to retrieve (max 100)
     * @param apiKey   Your API key for authentication
     *
     * @return A Call object that can be used to send the HTTP request asynchronously or synchronously.
     */

    @GET("top-headlines")  // HTTP GET request to the endpoint https://newsapi.org/v2/top-headlines
    Call<NewsArticle> getNews(
            @Query("country") String country,       // Query parameter: country=us
            @Query("pageSize") String pageSize,     // Query parameter: pageSize=100
            @Query("apiKey") String apiKey          // Query parameter: apiKey=your_api_key
    );

    /**
     * Get top headlines filtered by category for a specified country.
     *
     * @param country  The 2-letter country code (e.g., "us" for United States)
     * @param category The category to filter news by (e.g., "technology", "sports")
     * @param pageSize Number of articles to retrieve (max 100)
     * @param apiKey   Your API key for authentication
     *
     * @return A Call object to execute the HTTP request.
     */

    @GET("top-headlines")  // HTTP GET request to the same endpoint as above
    Call<NewsArticle> getCategory(
            @Query("country") String country,       // Query parameter: country=us
            @Query("category") String category,     // Query parameter: category=technology
            @Query("pageSize") String pageSize,     // Query parameter: pageSize=100
            @Query("apiKey") String apiKey          // Query parameter: apiKey=your_api_key
    );
}
