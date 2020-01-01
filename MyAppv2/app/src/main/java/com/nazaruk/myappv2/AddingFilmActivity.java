package com.nazaruk.myappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddingFilmActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText authorEditText;
    private EditText yearEditText;
    private EditText descriptionEditText;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Button mSubmitButton;
    private ViewGroup mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_film);
        initForm();
    }

    private void initForm() {
        nameEditText = findViewById(R.id.film_name);
        authorEditText = findViewById(R.id.film_author);
        yearEditText = findViewById(R.id.film_year);
        descriptionEditText = findViewById(R.id.film_description);
        mImageView = findViewById(R.id.item_image);
        mProgressBar = findViewById(R.id.progress_bar);
        mSubmitButton = findViewById(R.id.btn_create_item);
        mainLayout = findViewById(R.id.main_layout);

        Picasso.get().load(Constants.MOVIE_PHOTO_URL.getValue()).into(mImageView);

        mSubmitButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String author = authorEditText.getText().toString();
            String year = yearEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            postFilm(new Film(name, author, year, description));
        });
    }

    private void postFilm(final Film film) {
        setProgressBarVisible(true);
        DataApi api = ((ApplicationStaff) this.getApplication()).getApi();
        Call<Film> call = api.postFilm(film);

        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                setProgressBarVisible(false);
                startActivityForResult(new Intent(getApplicationContext(), MainActivity.class), 0);
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Snackbar.make(mainLayout, "Post request failed", Snackbar.LENGTH_LONG);
                setProgressBarVisible(false);
            }
        });
    }

    private void setProgressBarVisible(boolean isVisible) {
        if (isVisible) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);

        }
    }
}
