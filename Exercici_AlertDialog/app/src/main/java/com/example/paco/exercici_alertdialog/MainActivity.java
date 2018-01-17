package com.example.paco.exercici_alertdialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FechaDialogFragment.DialogoFechaInterfaz, HoraDialogFragment.DialogoHoraInterfaz {

    private Button botonFecha, botonHora, botonColor;
    private TextView textoFecha, textoHora, textoColor;

    private AlertDialog.Builder adBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperamos elementos del layout
        botonFecha = (Button)findViewById(R.id.botonFecha);
        botonHora = (Button)findViewById(R.id.botonHora);
        botonColor = (Button)findViewById(R.id.botonColor);
        textoFecha = (TextView)findViewById(R.id.textoFecha);
        textoHora = (TextView)findViewById(R.id.textoHora);
        textoColor = (TextView)findViewById(R.id.textoColor);


        //Añadimos listener a los botones
        botonFecha.setOnClickListener(this);
        botonHora.setOnClickListener(this);
        botonColor.setOnClickListener(this);


        adBuilder = new AlertDialog.Builder(MainActivity.this);

    }

    //Método onClick para todos los botones
    public void onClick(View v) {

        //Comprobamos sobre qué botón se ha hecho click
        if(v.getId() == R.id.botonFecha){

            FechaDialogFragment fDf = new FechaDialogFragment();
            fDf.show(getSupportFragmentManager(), "datepicker");
        }
        else if(v.getId() == R.id.botonHora){

            HoraDialogFragment hDf = new HoraDialogFragment();
            hDf.show(getSupportFragmentManager(), "timepicker");
        }
        else if(v.getId() == R.id.botonColor){
            mostrarDialogoColor();
        }
    }

    private void mostrarDialogoColor(){
        adBuilder.setTitle("Color").setItems(R.array.array_colores, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int posicion) {
                if(posicion == 0){
                    textoColor.setText("COLOR - Blau");
                }
                else if(posicion == 1){
                    textoColor.setText("COLOR - Verd");
                }
                else if(posicion == 2){
                    textoColor.setText("COLOR - Roig");
                }

            }
        });

        //Lo mostramos
        adBuilder.create().show();
    }

    @Override
    public void actualizarFecha(int anyo, int mes, int dia) {
        textoFecha.setText("DATA -" + " "+dia+"/"+mes+"/"+anyo);
    }

    @Override
    public void actualizarHora(int hora, int minuto) {

        if(minuto<10){
            if(hora<10) {
                textoHora.setText("HORA: 0" + hora + ":0" + minuto);
            }
            else{
                textoHora.setText("HORA: 0" + hora + ":0" + minuto);
            }

        }
        else {
            if(hora<10) {
                textoHora.setText("HORA - 0" + hora + ":" + minuto);
            }
            else{
                textoHora.setText("HORA: " + hora + ":" + minuto);
            }
        }
    }
}
