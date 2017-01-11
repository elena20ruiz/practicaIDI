package com.example.pr_idi.mydatabaseexample.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.pr_idi.mydatabaseexample.Film;
import com.example.pr_idi.mydatabaseexample.FilmData;
import com.example.pr_idi.mydatabaseexample.R;
import com.example.pr_idi.mydatabaseexample.RecyclerViewAdapter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;





public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private FilmData filmData;
    private FloatingActionButton fabMenu;
    private FloatingActionButton fabCreate;
    private RecyclerView rv;
    private Context context;
    private Boolean aquesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        filmData = new FilmData(this);
        filmData.open();
            //inizialitzaData();

        setContentView(R.layout.activity_main);

        aquesta = false;

        //CONTEXT-------------------------------------------------------------------------------
        context = this;

        //TOOLBAR-------------------------------------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FLOATTING BUTTON------------------------------------------------------------------------
        fabMenu = (FloatingActionButton) findViewById(R.id.menu_fab);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertFilmIntent = new Intent(context, CreationActivity.class);
                startActivity(insertFilmIntent);
            }
        });

        //ACTION BAR-------------------------------------------------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NAVIGATION VIEW--------------------------------------------------------------------------
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //RECYCLE VIEW------------------------------------------------------------------------------

        List<Film> filmList = filmData.getAllFilmsByTitle();

        rv = (RecyclerView)findViewById(R.id.cardList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //filmList = filmData.orderByTitle();
        RecyclerViewAdapter rva = new RecyclerViewAdapter(filmList);
        rv.setAdapter(rva);
    }

    private void inizialitzaData() {
        filmData.createCompleteFile("Ben Hur", "Timur Bekmambetov", "Jack Huston", 2016, 8,"Estados Unidos", 2);
        filmData.createCompleteFile("El mensajero", "Ric Roman Waugh", "Dwayne Johnson", 2013, 7,"Estados Unidos", 2);
        filmData.createCompleteFile("El lado bueno de las cosas", "David O. Russell", "Bradley Cooper", 2012, 8,"Estados Unidos", 0);
        filmData.createCompleteFile("La ola", "Dennis Gansel", "Jürgen Vogel", 2008, 9,"Alemania", 0);
        filmData.createCompleteFile("Aloha", "Cameron Crowe", "Bradley Cooper", 2015, 5,"Estados Unidos", 1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);


        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                List<Film> filmList = filmData.getAllFilmsByTitle();
                RecyclerViewAdapter rva = new RecyclerViewAdapter(filter(filmList,query));
                rv.setAdapter(rva);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<Film> filmList = filmData.getAllFilmsByTitle();
                RecyclerViewAdapter rva = new RecyclerViewAdapter(filter(filmList,newText));
                rv.setAdapter(rva);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean isSelected = false;
        Intent intent;

        switch (id) {
            case R.id.nav_slideshow:
                intent = new Intent(this, RecycleViewForYear.class);
                startActivity(intent);
                break;
            case R.id.nav_manage:
                intent = new Intent(this, CreationActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                ShowDialog("About","Fet per: David Aleu Moseguí \n              Elena Ruiz Cano \n \nContacto: david.aleu@est.fib.upc.edu\n                  elena.ruiz@est.fib.upc.edu");
                break;
            case R.id.help:
                ShowDialog("Help"," 1. Com puc crear una nova pel·lícula? \nA la part de baix a la dreta es troba un botó, on al clicar s'obra una nova pantalla per poder introduir tota la informació respecte la pel·lícula.\n \n" +
                                     "2. Com puc esborrar o editar una pel·lícula? \nEn el menú que se'ns obre en clicar el botó que es troba amunt a l'esquerra, s'ha de clicar l'opció de Vista Detallada, on es veuran totes les pel·lícules on abaix de cadascuna trobem dos botons un per esborrar la corresponent pel·lícula, i l'altre per editar la seva valoració.\n \n" +
                        "3. Com puc veure tota la informació de les pel·lícules? \nEn el menú que se'ns obre en clicar el botó que es troba amunt a l'esquerra, s'ha de clicar l'opció de Vista Detallada, on es veuran totes les pel·lícules amb tota la seva informació.\n");
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ShowDialog(String title, String content){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(content);
        if(title.equals("About")) alertDialog.setIcon(android.R.drawable.ic_menu_info_details);
        else alertDialog.setIcon(android.R.drawable.ic_menu_help);
        //alertDialog.setFeatureDrawableResource(question_mark,0);
        alertDialog.setCancelable(true);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!aquesta) aquesta = true;
        else {
            Intent intent = new Intent(this, com.example.pr_idi.mydatabaseexample.activity.MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Film> films = filmData.getAuthorFilms(query);
            RecyclerViewAdapter rva = new RecyclerViewAdapter(films);
            rv.setAdapter(rva);
        }
    }



    //AUXILIAR SEARCH
    private static List<Film> filter(List<Film> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Film> filteredModelList = new ArrayList<>();
        for (Film model : models) {
            final String text = model.getProtagonist().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}
