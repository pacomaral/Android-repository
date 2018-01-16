package com.example.paco.probant_alertdialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button botonMostrar;

    private AlertDialog.Builder adBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonMostrar = (Button)findViewById(R.id.botonMostrarDialogo);
        adBuilder = new AlertDialog.Builder(MainActivity.this);


        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoArray();
            }
        });
    }



    private void mostrarDialogo(){

        adBuilder.setMessage("¿Te gusta este diálogo?").setTitle("Mensaje");

        adBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Te gusta el diálogo", Toast.LENGTH_SHORT).show();
            }
        });

        adBuilder.setNegativeButton("No", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "No te gusta el diálogo", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = adBuilder.create();
        dialog.show();
    }

    private void mostrarDialogoArray(){
        adBuilder.setTitle("Color").setItems(R.array.array_colores, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int posicion) {
                if(posicion == 0){
                    Toast.makeText(getApplicationContext(), "Has elegido Rojo", Toast.LENGTH_SHORT).show();
                }
                else if(posicion == 1){
                    Toast.makeText(getApplicationContext(), "Has elegido Verde", Toast.LENGTH_SHORT).show();
                }
                else if(posicion == 2){
                    Toast.makeText(getApplicationContext(), "Has elegido Azul", Toast.LENGTH_SHORT).show();
                }

            }
        });

        adBuilder.create().show();
    }
}
