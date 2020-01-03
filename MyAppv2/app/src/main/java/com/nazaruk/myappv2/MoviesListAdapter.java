package com.nazaruk.myappv2;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.common.util.CollectionUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.FilmViewHolder> {
    private List<Film> films;
    private Activity activity;

    public MoviesListAdapter(List<Film> films, Activity activity) {
        this.films = films;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new FilmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FilmViewHolder filmViewHolder, final int i) {
        final Film currentMovie = films.get(i);

        Picasso.get().load(Constants.MOVIE_PHOTO_URL.getValue()).into(filmViewHolder.avatar);
        filmViewHolder.filmName.setText(currentMovie.getName());
        filmViewHolder.author.setText(currentMovie.getAuthor());
        filmViewHolder.year.setText(currentMovie.getYear());
        filmViewHolder.description.setText(currentMovie.getDescription());

        filmViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailsActivity.class);
            intent.putExtra(Constants.MOVIE_NAME.getValue(), currentMovie.getName());
            intent.putExtra(Constants.MOVIE_AUTHOR.getValue(), currentMovie.getAuthor());
            intent.putExtra(Constants.MOVIE_DESCRIPTION.getValue(), currentMovie.getDescription());
            intent.putExtra(Constants.MOVIE_PHOTO_URL.getValue(), currentMovie.getPhotoUrl());
            intent.putExtra(Constants.MOVIE_YEAR.getValue(), currentMovie.getYear());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }


    public void updateFilms(List<Film> films) {
        Collections.reverse(films);
        this.films = films;
        notifyDataSetChanged();
    }


    public static class FilmViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView avatar;
        private TextView filmName;
        private TextView author;
        private TextView year;
        private TextView description;
        private SwipeRefreshLayout refreshLayout;

        FilmViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            refreshLayout = (SwipeRefreshLayout) itemView.findViewById(R.id.swipe);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            filmName = (TextView) itemView.findViewById(R.id.film_name);
            author = (TextView) itemView.findViewById(R.id.film_author);
            year = (TextView) itemView.findViewById(R.id.year);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

}

