package com.nazaruk.myappv2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {
    @GET("films")
    Call<List<Film>> getFilm();
}
