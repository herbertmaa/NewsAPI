package com.bcit.MA_KWON;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class News implements Serializable {
    @SerializedName("status")
    private String responseStatus;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private ArrayList<Article> articles;

    public News(String status, int totalResults, ArrayList<Article> articles) {
        this.responseStatus = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
}
