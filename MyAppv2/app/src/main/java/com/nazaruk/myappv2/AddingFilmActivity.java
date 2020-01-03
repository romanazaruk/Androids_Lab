package com.nazaruk.myappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private EditText mNameEditText;
    private EditText mAuthorEditText;
    private EditText mYearEditText;
    private EditText mDescriptionEditText;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Button mSubmitButton;
    private RestUtil mRestUtill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_film);

        initForm();
        initPhoto();
        mRestUtill = new RestUtil((this));
    }

    private void initForm() {
        mNameEditText = findViewById(R.id.film_name);
        mAuthorEditText = findViewById(R.id.film_author);
        mYearEditText = findViewById(R.id.film_year);
        mDescriptionEditText = findViewById(R.id.film_description);
        mImageView = findViewById(R.id.item_image);
        mProgressBar = findViewById(R.id.progress_bar);
        mSubmitButton = findViewById(R.id.btn_create_item);

        mSubmitButton.setOnClickListener(v -> {
            String name = mNameEditText.getText().toString();
            String author = mAuthorEditText.getText().toString();
            String year = mYearEditText.getText().toString();
            String description = mDescriptionEditText.getText().toString();

            setProgressBarVisible(true);
            mRestUtill.postFilm(new Film(name, author, year, description), new Callback<Film>() {
                @Override
                public void onResponse(Call<Film> call, Response<Film> response) {
                    setProgressBarVisible(false);
                    startActivityForResult(new Intent(getApplicationContext(), MainActivity.class), 0);
                }

                @Override
                public void onFailure(Call<Film> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.content), getString(R.string.post_request_failed), Snackbar.LENGTH_LONG);
                    setProgressBarVisible(false);
                }
            });
        });
    }

    private void initPhoto() {
        Picasso.get().load(Constants.MOVIE_PHOTO_URL.getValue()).into(mImageView);
    }

    private void setProgressBarVisible(boolean isVisible) {
        mProgressBar.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
