package com.example.pr_idi.mydatabaseexample;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FilmViewHolder>{

    List<Film> films;

    public RecyclerViewAdapter(List<Film> films){
        this.films = films;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new FilmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int i) {

        Film f = films.get(i);

        holder.title_film.setText(f.getTitle());
        holder.director.setText(f.getDirector());
    }



    @Override
    public int getItemCount() {
       try {
           return films.size();
       }
       catch (Exception e) {
           return 0;
       }

    }

    public static class FilmViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title_film;
        TextView director;

        FilmViewHolder(View itemView) {
            super(itemView);

            cv =            (CardView) itemView.findViewById(R.id.card_view);
            title_film =    (TextView) itemView.findViewById(R.id.title_film);
            director =      (TextView) itemView.findViewById(R.id.director);
        }
    }

}