package com.scheffer.erik.popularmovies.MovieDatabaseApi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.scheffer.erik.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movie> {
    public MoviesAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }

        Movie movie = getItem(position);
        if (movie != null) {
            Picasso.with(getContext())
                    .load("http://image.tmdb.org/t/p/w500//" + movie.getPosterPath())
                    .into((ImageView) convertView.findViewById(R.id.movie_poster));
        }

        return convertView;
    }
}