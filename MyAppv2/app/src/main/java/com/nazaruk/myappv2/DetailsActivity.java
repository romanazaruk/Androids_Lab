package com.nazaruk.myappv2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    ImageView avatarIv;
    TextView filmNameTv;
    TextView authorTv;
    TextView yearTv;
    TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        avatarIv = findViewById(R.id.avatar);
        filmNameTv = findViewById(R.id.film_name);
        authorTv = findViewById(R.id.film_author);
        yearTv = findViewById(R.id.year);
        descriptionTv = findViewById(R.id.description);

        String filmId = getIntent().getStringExtra(Constants.MOVIE_ID.getValue());

        if (filmId != null) {
            showFilmById(filmId);

        } else {
            Picasso.get().load(Constants.MOVIE_PHOTO_URL.getValue()).into(avatarIv);
            filmNameTv.setText(getIntent().getStringExtra(Constants.MOVIE_NAME.getValue()));
            authorTv.setText(getIntent().getStringExtra(Constants.MOVIE_AUTHOR.getValue()));
            yearTv.setText(getIntent().getStringExtra(Constants.MOVIE_YEAR.getValue()));
            descriptionTv.setText(getIntent().getStringExtra(Constants.MOVIE_DESCRIPTION.getValue()));
        }
    }

    private void showFilmById(final String filmId) {
        final Call<List<Film>> call = getApplicationEx().getApi().getFilm();
        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {

                for (Film film: Objects.requireNonNull(response.body())) {
                    if (film.getName().equals(filmId)){
                        Picasso.get().load(Constants.MOVIE_PHOTO_URL.getValue()).into(avatarIv);
                        filmNameTv.setText(film.getName());
                        authorTv.setText(film.getAuthor());
                        yearTv.setText(film.getYear());
                        descriptionTv.setText(film.getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {

            }
        });
    }

    private ApplicationStaff getApplicationEx() {
        return (ApplicationStaff) this.getApplication();
    }
}

