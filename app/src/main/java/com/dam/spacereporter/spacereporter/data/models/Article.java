package com.dam.spacereporter.spacereporter.data.models;

import android.annotation.SuppressLint;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Article {

    private final Integer id;
    private final String title, url, imageUrl, newsSite, summary;
    @Nullable
    private final Date publishedAt;
    @Nullable
    private final Date updatedAt;

    // Minimal constructor
    public Article(Integer id, String title, String url, String imageUrl, String newsSite, String summary) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.newsSite = newsSite;
        this.summary = summary;
        publishedAt = null;
        updatedAt = null;
    }

    // Almost full constructor (missing list of "launches")
    public Article(Integer id, String title, String url, String imageUrl, String newsSite, String summary, @Nullable Date publishedAt, @Nullable Date updatedAt) {
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
    public final Integer getId() {
        return id;
    }

    /**
     * Returns the title of the article
     *
     * @return Title of the article
     */
    @SerializedName("title")
    public final String getTitle() {
        return title;
    }

    /**
     * Returns the web URI (from the stored URL) of the article
     *
     * @return Uri of the article
     */
    @SerializedName("url")
    public final Uri getUrl() {
        return Uri.parse(url);
    }

    /**
     * Returns the image URL of the article
     *
     * @return Image URL of the article
     */
    @SerializedName("imageUrl")
    public final String getImageUrl() {
        return imageUrl;
    }

    /**
     * Returns the news site of the article
     *
     * @return News site of the article
     */
    @SerializedName("newsSite")
    public final String getNewsSite() {
        return newsSite;
    }

    /**
     * Returns the summary of the article
     *
     * @return Summary of the article
     */
    @SerializedName("summary")
    public final String getSummary() {
        return summary;
    }

    /**
     * Returns the publishing date site of the article
     *
     * @return Publish date of the article
     */
    @Nullable
    @SerializedName("publishedAt")
    public final Date getPublishedAt() {
        return publishedAt;
    }

    /**
     * Returns the last edit date of the article
     *
     * @return Date of the last edit of the article
     */
    @Nullable
    @SerializedName("updatedAt")
    public final Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Returns the string representation of the article
     *
     * @return Article as string
     */
    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format(
                "Article{id=%d, title='%s', url='%s', imageUrl='%s', newsSite='%s', summary='%s'}",
                id, title, url, imageUrl, newsSite, summary);
    }
}
