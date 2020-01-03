package com.nazaruk.myappv2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataApi {
    @GET("film")
    Call<List<Film>> getFilm();

    @POST("film")
    Call<Film> postFilm(@Body Film film);
}
