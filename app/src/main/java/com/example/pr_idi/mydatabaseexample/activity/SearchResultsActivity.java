package com.example.pr_idi.mydatabaseexample.activity;


import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.pr_idi.mydatabaseexample.Film;
import com.example.pr_idi.mydatabaseexample.FilmData;
import com.example.pr_idi.mydatabaseexample.R;
import com.example.pr_idi.mydatabaseexample.RecyclerViewAdapter;

import java.util.List;

public class SearchResultsActivity extends Activity {

    private FilmData fm = new FilmData(this);
    private RecyclerView rv = new RecyclerView(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            List<Film> filmList = fm.getAuthorFilms(query);

            rv = (RecyclerView)findViewById(R.id.cardList2);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            rv.setLayoutManager(llm);

            //filmList = filmData.orderByTitle();
            RecyclerViewAdapter rva= new RecyclerViewAdapter(filmList);
            rv.setAdapter(rva);
        }
    }


}