package com.bcit.MA_KWON;

public class DisplayItem {

    private String articleTitle, articleDetail, articleImage;

    public DisplayItem(String articleTitle, String articleDetail, String articleImage) {
        this.articleTitle = articleTitle;
        this.articleDetail = articleDetail;
        this.articleImage = articleImage;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleDetail() {
        return articleDetail;
    }

    public String getArticleImage() {
        return articleImage;
    }
}
