package com.example.paco.ejercicio_4a_aad.controller_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paco.ejercicio_4a_aad.R;
import com.example.paco.ejercicio_4a_aad.model.Adaptador_DB;
import com.example.paco.ejercicio_4a_aad.model.Asignatura;

public class Activity_Asignatura extends AppCompatActivity {

    private EditText cajaNombre, cajaHoras;
    private Button botonAnyadir;

    private String nombre, horas;

    private Adaptador_DB adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__asignatura);

        cajaNombre = (EditText)findViewById(R.id.cajaNombreAsignatura);
        cajaHoras = (EditText)findViewById(R.id.cajaHorasAsignatura);
        botonAnyadir = (Button)findViewById(R.id.botonNuevaAsignatura);


        //Listener para el botón
        botonAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(obtenerDatos()){
                    Asignatura a = new Asignatura(nombre, horas);

                    abrirBD();

                    //Insertamos asignatura
                    adaptador.insertarAsignatura(a);

                    Toast.makeText(getApplicationContext(), "Asignatura añadida con éxito", Toast.LENGTH_SHORT).show();

                    //Cerramos activity
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error. Algún dato es inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean obtenerDatos(){

        if(cajaNombre.getText().toString().isEmpty() || cajaHoras.getText().toString().isEmpty()){
            return false;
        }
        else{
            nombre = cajaNombre.getText().toString();
            horas = cajaHoras.getText().toString();
            return true;
        }
    }


    private void abrirBD(){
        //Creamos el objeto del adaptador
        adaptador = new Adaptador_DB(this);
        //Abrimos
        adaptador.abrir();
    }
}
