package com.nazaruk.myappv2;


public enum Constants {
    MOVIE_ID("MOVIE_ID"),
    MOVIE_PHOTO_URL("https://www.freepnglogos.com/uploads/netflix-logo-circle-png-5.png"),
    MOVIE_NAME("MOVIE_NAME"),
    MOVIE_AUTHOR("MOVIE_AUTHOR"),
    MOVIE_YEAR("MOVIE_YEAR"),
    MOVIE_DESCRIPTION("MOVIE_DESCRIPTION");

    private String value;

    Constants(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}