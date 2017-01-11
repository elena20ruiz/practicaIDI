package com.example.pr_idi.mydatabaseexample;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by aleue on 9/1/2017.
 */

public class RecycleViewAdapterForYear extends RecyclerView.Adapter<RecycleViewAdapterForYear.FilmViewHolderForYear> {

    List<Film> films;
    Context context;

    public RecycleViewAdapterForYear(List<Film> films, Context context){
        this.context = context;
        this.films = films;
    }

    @Override
    public FilmViewHolderForYear onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_detail, parent, false);
        return new FilmViewHolderForYear(itemView);
    }

    @Override
    public void onBindViewHolder(FilmViewHolderForYear holder, int i) {

        final Film f = films.get(i);

        Log.e("film",f.toString());

        holder.title_film.setText(f.getTitle());
        holder.director.setText(f.getDirector());
        holder.protagonista.setText(f.getProtagonist());
        holder.country.setText(f.getCountry());
        holder.year.setText(String.valueOf(f.getYear()));
        holder.rating.setText(String.valueOf(f.getCritics_rate()) +"/10");
        holder.id.setText(String.valueOf(f.getId()));

        float auxRat = (float) f.getCritics_rate();
        auxRat = auxRat/2;
        holder.rat.setRating(auxRat);

        holder.botoborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilmData filmData = new FilmData(context);
                filmData.open();
                long id = f.getId();
                filmData.deleteFilmWithId((int)id);

                Intent intent = ((Activity)context).getIntent();
                ((Activity)context).finish();
                context.startActivity(intent);
            }
        });

        if(f.getIdTheme() == 0) holder.imageView.setImageResource(R.drawable.drama);
        else if(f.getIdTheme() == 1) holder.imageView.setImageResource(R.drawable.comedy);
        else if(f.getIdTheme() == 2) holder.imageView.setImageResource(R.drawable.action);
        else if(f.getIdTheme() == 3) holder.imageView.setImageResource(R.drawable.terror);
        else if(f.getIdTheme() == 4) holder.imageView.setImageResource(R.drawable.musical);
        else if(f.getIdTheme() == 5) holder.imageView.setImageResource(R.drawable.fiction);
        else if(f.getIdTheme() == 6) holder.imageView.setImageResource(R.drawable.infantil);

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

    public static class FilmViewHolderForYear extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title_film;
        TextView director;
        TextView protagonista;
        TextView year;
        TextView country;
        TextView rating;
        TextView id;
        ImageView imageView;
        Button botoborrar;
        RatingBar rat;

        FilmViewHolderForYear(View itemView) {
            super(itemView);

            cv =            (CardView) itemView.findViewById(R.id.card_view2);
            title_film =    (TextView) itemView.findViewById(R.id.title_film);
            director =      (TextView) itemView.findViewById(R.id.directorName);
            imageView =     (ImageView) itemView.findViewById(R.id.image_theme);
            protagonista =    (TextView) itemView.findViewById(R.id.protagonistaName);
            year =      (TextView) itemView.findViewById(R.id.anyName);
            country =    (TextView) itemView.findViewById(R.id.paisName);
            rating =      (TextView) itemView.findViewById(R.id.puntuacioName);
            id =      (TextView) itemView.findViewById(R.id.id);
            botoborrar =      (Button) itemView.findViewById(R.id.borrar);
            rat = (RatingBar) itemView.findViewById(R.id.ratingBar);


        }
    }
}
