package com.nazaruk.myappv2;

import android.net.Uri;

//import com.google.gson.annotations.SerializedName;

public class Film {
    private String name;
    private String author;
    private String year;
    private String description;
    private String photo;

    public Film(String name, String author, String year,String description, String photo) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.description = description;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public Uri getPhoto() {
        return Uri.parse(photo);
    }
}
