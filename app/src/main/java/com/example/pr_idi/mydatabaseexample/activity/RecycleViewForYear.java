package com.example.pr_idi.mydatabaseexample.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.pr_idi.mydatabaseexample.Film;
import com.example.pr_idi.mydatabaseexample.FilmData;
import com.example.pr_idi.mydatabaseexample.MySQLiteHelper;
import com.example.pr_idi.mydatabaseexample.R;
import com.example.pr_idi.mydatabaseexample.RecycleViewAdapterForYear;
import com.example.pr_idi.mydatabaseexample.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewForYear extends AppCompatActivity {

    private FilmData filmData;
    private ImageButton back;
    private RecyclerView rv;
    //private MySQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_for_year);

        //INICIALITZACIÓ DEL TOOLBAR --------------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //INICIALITZACIO DEL BOTO BACK -------------------------------------------------------
        back = (ImageButton) findViewById(R.id.gobackbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //AMAGAR EL TECLAT INICIALMENT --------------------------------------------------------
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //CREAR INSTANCIA DE LA BD ------------------------------------------------------------
        filmData = new FilmData(this);
        filmData.open();


        /*helper = new MySQLiteHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(MySQLiteHelper.TABLE_FILMS, null, null);*/

        List<Film> filmList = filmData.getAllFilms();

        rv = (RecyclerView)findViewById(R.id.cardList2);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RecycleViewAdapterForYear rva= new RecycleViewAdapterForYear(filmList);
        rv.setAdapter(rva);

    }

}
