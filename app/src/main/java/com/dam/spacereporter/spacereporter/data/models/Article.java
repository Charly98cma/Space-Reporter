package com.dam.spacereporter.spacereporter.data.models;

import java.util.Date;
import java.util.List;

public class Article {

    private final Integer id;
    private final String title, url, imageUrl, newsSite, summary;
    private final Date publishedAt, updatedAt;
    private final boolean featured;
    private final List launches;

    // FIXME Review the structure and datatypes

    public Article(Integer id,
                   String title,
                   String url,
                   String imageUrl,
                   String newsSite,
                   String summary,
                   Date publishedAt,
                   Date updatedAt,
                   boolean featured,
                   List launches) {

        this.id = id;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.newsSite = newsSite;
        this.summary = summary;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
        this.featured = featured;
        this.launches = launches;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNewsSite() {
        return newsSite;
    }

    public String getSummary() {
        return summary;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean isFeatured() {
        return featured;
    }

    public List getLaunches() {
        return launches;
    }
}
