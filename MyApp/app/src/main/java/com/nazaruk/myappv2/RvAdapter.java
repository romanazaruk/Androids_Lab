package com.nazaruk.myappv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.FilmViewHolder> {
    private List<Film> films;

    public RvAdapter(List<Film> films) {
        this.films = films;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        FilmViewHolder filmViewHolder = new FilmViewHolder(v);
        return filmViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilmViewHolder filmViewHolder, final int i) {
        Picasso.get().load(films.get(i).getPhoto()).into(filmViewHolder.avatar);
        filmViewHolder.filmName.setText(films.get(i).getName());
        filmViewHolder.author.setText(films.get(i).getAuthor());
        filmViewHolder.year.setText(films.get(i).getYear());
        filmViewHolder.description.setText(films.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return films.size();
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

