package com.dam.spacereporter.spacereporter.data.models;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Article {

    private final Integer id;
    private final String title, url, imageUrl, newsSite, summary;
    private final Date publishedAt, updatedAt;

    // Minimal constructor
    public Article(Integer id, String title, String url, String imageUrl, String newsSite, String summary) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.newsSite = newsSite;
        this.summary = summary;
        this.publishedAt = null;
        this.updatedAt = null;
    }

    // Almost full constructor (missing list of "launches")
    public Article(Integer id, String title, String url, String imageUrl, String newsSite, String summary, Date publishedAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.newsSite = newsSite;
        this.summary = summary;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the id of the article
     *
     * @return ID of the article
     */
    @SerializedName("id")
    public Integer getId() {
        return id;
    }

    /**
     * Returns the title of the article
     *
     * @return Title of the article
     */
    @SerializedName("title")
    public String getTitle() {
        return title;
    }

    /**
     * Returns the web URI (from the stored URL) of the article
     *
     * @return Uri of the article
     */
    @SerializedName("url")
    public Uri getUrl() {
        return Uri.parse(url);
    }

    /**
     * Returns the image URL of the article
     *
     * @return Image URL of the article
     */
    @SerializedName("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Returns the news site of the article
     *
     * @return News site of the article
     */
    @SerializedName("newsSite")
    public String getNewsSite() {
        return newsSite;
    }

    /**
     * Returns the summary of the article
     *
     * @return Summary of the article
     */
    @SerializedName("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * Returns the publishing date site of the article
     *
     * @return Publish date of the article
     */
    @SerializedName("publishedAt")
    public Date getPublishedAt() {
        return publishedAt;
    }

    /**
     * Returns the last edit date of the article
     *
     * @return Date of the last edit of the article
     */
    @SerializedName("updatedAt")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Returns the string representation of the article
     *
     * @return Article as string
     */
    @NonNull
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", newsSite='" + newsSite + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
