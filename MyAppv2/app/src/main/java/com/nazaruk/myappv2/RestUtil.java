package com.nazaruk.myappv2;

import android.app.Activity;

import retrofit2.Call;
import retrofit2.Callback;

public class RestUtil {
    private Activity mActivity;

    public RestUtil(Activity activity) {
        this.mActivity = activity;
    }

    public void postFilm(final Film film, Callback<Film> callback) {
        DataApi api = ((NetworkService) mActivity.getApplication()).getApi();
        Call<Film> call = api.postFilm(film);

        call.enqueue(callback);
    }

}
