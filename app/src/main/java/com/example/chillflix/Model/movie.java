package com.example.chillflix.Model;

import androidx.annotation.NonNull;

public class movie {
    private long movieId;
    private String title;
    private String language;
    private String imgUrl;

    public movie(@NonNull long movieId, @NonNull String title, @NonNull String language, @NonNull String imgUrl) {
        this.movieId = movieId;
        this.title = title;
        this.language = language;
        this.imgUrl = imgUrl;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "movie{" +
                "movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}

