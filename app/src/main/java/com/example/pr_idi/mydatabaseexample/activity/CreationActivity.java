package com.example.pr_idi.mydatabaseexample.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.pr_idi.mydatabaseexample.*;

public class CreationActivity extends AppCompatActivity {

    private EditText titol;
    private EditText director;
    private EditText protagonista;
    private EditText any;
    private EditText puntuacio;
    private EditText pais;
    private Spinner categoria;

    private FilmData filmData;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

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

        //INICIALITZACIÓ DELS CAMPS D'EDICIÓ --------------------------------------------------------
        titol = (EditText) findViewById(R.id.titol);
        director = (EditText) findViewById(R.id.director);
        protagonista = (EditText) findViewById(R.id.protagonista);
        any = (EditText) findViewById(R.id.year);
        puntuacio = (EditText) findViewById(R.id.nota);
        pais = (EditText) findViewById(R.id.pais);
        categoria = (Spinner) findViewById(R.id.spinner);
    }

    public void ValidarForm(View _view){
        String titolText = titol.getText().toString();
        if(titolText.isEmpty()) ShowDialog("Dades Mal Introduides","S'ha d'introduir el títol de la pel·lícula.");
        else {
            String paisText = pais.getText().toString();
            if (paisText.isEmpty())
                ShowDialog("Dades Mal Introduides", "S'ha d'introduir el país on es va rodar.");
            else {
                String anyText = any.getText().toString();
                if (anyText.isEmpty())
                    ShowDialog("Dades Mal Introduides", "S'ha d'introduir un any d'estrena, entre els 1970 i l'any actual.");
                else {
                    String directorText = director.getText().toString();
                    if (directorText.isEmpty())
                        ShowDialog("Dades Mal Introduides", "S'ha d'introduir el director de la pel·lícula.");
                    else {
                        String protagonistaText = protagonista.getText().toString();
                        if (protagonistaText.isEmpty())
                            ShowDialog("Dades Mal Introduides", "S'ha d'introduir el protagonista de la pel·lícula.");
                        else {
                            String putuacioFloat = puntuacio.getText().toString();
                            if (putuacioFloat.isEmpty())
                                ShowDialog("Dades Mal Introduides", "S'ha d'introduir la nota de les crítiques.");
                            else{
                                int anyEstrena = Integer.parseInt(anyText);
                                int puntuacio = Integer.parseInt(putuacioFloat);
                                if(anyEstrena < 1970 || anyEstrena > 2017) ShowDialog("Dades Mal Introduides", "L'any d'estrena només pot anar del 1970 al 2017.");
                                else {
                                    if(puntuacio < 0 || puntuacio > 10) ShowDialog("Dades Mal Introduides", "La puntuació només pot anar del 0 al 10.");
                                    else {
                                        int cat = 0;
                                        Log.e("categoria",categoria.getSelectedItem().toString());
                                        if(categoria.getSelectedItem().toString().equals("Drama")) cat = 0;
                                        else if(categoria.getSelectedItem().toString().equals("Comedia")) cat = 1;
                                        else if(categoria.getSelectedItem().toString().equals("Acction")) cat = 2;
                                        else if(categoria.getSelectedItem().toString().equals("Terror")) cat = 3;
                                        else if(categoria.getSelectedItem().toString().equals("Musical")) cat = 4;
                                        else if(categoria.getSelectedItem().toString().equals("Ciéncia Ficció")) cat = 5;
                                        else if(categoria.getSelectedItem().toString().equals("Infantil")) cat = 6;
                                        filmData.createCompleteFile(titolText,directorText,protagonistaText,anyEstrena,puntuacio,paisText,cat);
                                        Intent intent = new Intent(this, com.example.pr_idi.mydatabaseexample.activity.MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void ShowDialog(String title, String content){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(content);
        //alertDialog.setIcon();
        alertDialog.setCancelable(true);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
