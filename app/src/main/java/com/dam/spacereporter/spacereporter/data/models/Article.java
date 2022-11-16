package com.dam.spacereporter.spacereporter.data.models;

import com.google.gson.annotations.SerializedName;

public class Article {

    private final Integer id;
    private final String title, url, imageUrl, newsSite, summary;
    // FIXME Add the rest of the fields to the upgraded version

    public Article(Integer id, String title, String url, String imageUrl, String newsSite, String summary) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.newsSite = newsSite;
        this.summary = summary;
    }

    @SerializedName("id")
    public Integer getId() {
        return id;
    }

    @SerializedName("title")
    public String getTitle() {
        return title;
    }

    @SerializedName("url")
    public String getUrl() {
        return url;
    }

    @SerializedName("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    @SerializedName("newsSite")
    public String getNewsSite() {
        return newsSite;
    }

    @SerializedName("summary")
    public String getSummary() {
        return summary;
    }
}
