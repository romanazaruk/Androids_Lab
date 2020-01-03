package com.nazaruk.myappv2;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService extends Application {
    private DataApi api;
    public static String URL = "https://rnazaruk-backend.appspot.com/";


    public void onCreate() {
        super.onCreate();
        api = createApi();
    }

    public DataApi getApi() {
        return api;
    }

    public DataApi createApi() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(DataApi.class);
    }
}
