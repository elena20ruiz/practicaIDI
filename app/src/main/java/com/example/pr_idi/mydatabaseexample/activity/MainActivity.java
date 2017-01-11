package com.example.pr_idi.mydatabaseexample.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FilmData filmData;
    private FloatingActionButton fabMenu;
    private FloatingActionButton fabCreate;
    private RecyclerView rv;
    private RecyclerViewAdapter adapter;
    private Context context;
    private Boolean aquesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
        filmData = new FilmData(this);
        filmData.open();
        List<Film> filmList = filmData.getAllFilmsByYear();

        /*filmList.add(new Film(0,"Phocahontas","Anonimo"));
        filmList.add(new Film(1,"Jesucristo SuperStar","Dios"));*/


        rv = (RecyclerView)findViewById(R.id.cardList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //filmList = filmData.orderByTitle();
        RecyclerViewAdapter rva= new RecyclerViewAdapter(filmList);
        rv.setAdapter(rva);

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

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onSearchRequested(){

        Bundle appData = new Bundle();
        appData.putString("hello", "world");
        startSearch(null, false, appData, false);
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
            case R.id.nav_principal:
                isSelected = true;
                break;
            case R.id.nav_help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_slideshow:
                intent = new Intent(this, RecycleViewForYear.class);
                startActivity(intent);
                break;
            case R.id.about:
                ShowDialog("About","Hecho por: PickAnEvent.SA \nContacto: support@pickanevent.com");
                break;
            case R.id.help:
                ShowDialog("Help","Con esta aplicación, podras consultar y buscar todos los eventos que sean de tu interés!\nDes de la vista principal, podras ver todos los eventos, grupos y amistades a los que assistes o sigues, y a través de la lupa que se encuentra arriva de la pantalla podràs buscar todo lo que quieras! :D");
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
        if(aquesta==false) aquesta = true;
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

}
