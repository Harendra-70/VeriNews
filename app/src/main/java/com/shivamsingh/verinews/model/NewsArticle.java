package com.shivamsingh.verinews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsArticle {
    @SerializedName("status")//Use @SerializedName to match JSON keys to fields (required if names differ).
    @Expose
    // If you have fields without @Expose, they won't be included when using excludeFieldsWithoutExposeAnnotation()
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @Expose
    private ArrayList<Model> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Model> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Model> articles) {
        this.articles = articles;
    }
}
