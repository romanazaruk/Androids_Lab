package com.nazaruk.myappv2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        ImageView avatarIv = (ImageView) findViewById(R.id.avatar);
        TextView filmNameTv = (TextView) findViewById(R.id.film_name);
        TextView authorTv = (TextView) findViewById(R.id.film_author);
        TextView yearTv = (TextView) findViewById(R.id.year);
        TextView descriptionTv = (TextView) findViewById(R.id.description);

        Picasso.get().load(getIntent().getStringExtra(Constants.MOVIE_PHOTO_URL.getValue())).into(avatarIv);
        filmNameTv.setText(getIntent().getStringExtra(Constants.MOVIE_NAME.getValue()));
        authorTv.setText(getIntent().getStringExtra(Constants.MOVIE_AUTHOR.getValue()));
        yearTv.setText(getIntent().getStringExtra(Constants.MOVIE_YEAR.getValue()));
        descriptionTv.setText(getIntent().getStringExtra(Constants.MOVIE_DESCRIPTION.getValue()));
    }
}

