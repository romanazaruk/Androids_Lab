package com.nazaruk.myappv2;

import android.net.Uri;

//import com.google.gson.annotations.SerializedName;

public class Film {
    final private String name;
    final private String author;
    final private String year;
    final private String description;
    final private String photo = "";

    public Film(final  String name,final  String author,final  String year, final String description) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.description = description;
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

    public String getPhotoUrl() {
        return photo;
    }
}
