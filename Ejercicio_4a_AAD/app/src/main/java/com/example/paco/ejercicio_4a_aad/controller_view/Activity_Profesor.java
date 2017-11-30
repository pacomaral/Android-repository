package com.example.paco.ejercicio_4a_aad.controller_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paco.ejercicio_4a_aad.R;
import com.example.paco.ejercicio_4a_aad.model.Adaptador_DB;
import com.example.paco.ejercicio_4a_aad.model.Estudiante;
import com.example.paco.ejercicio_4a_aad.model.Profesor;

public class Activity_Profesor extends AppCompatActivity {

    private EditText cajaNombre, cajaEdad, cajaCiclo, cajaCurso, cajaDespacho;
    private Button botonAnyadirProfesor;
    private Adaptador_DB adaptador;
    private Profesor p;

    private String nombre, ciclo, despacho;
    private int edad, curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profesor);

        //Recuperamos elementos del layout
        cajaNombre = (EditText)findViewById(R.id.cajaNombreProfesor);
        cajaEdad = (EditText)findViewById(R.id.cajaEdadProfesor);
        cajaCiclo = (EditText)findViewById(R.id.cajaCicloProfesor);
        cajaCurso = (EditText)findViewById(R.id.cajaCursoProfesor);
        cajaDespacho = (EditText)findViewById(R.id.cajaDespachoProfesor);
        botonAnyadirProfesor = (Button)findViewById(R.id.botonAnyadirProfesor);

        //Añadimos listener al botón
        botonAnyadirProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Si obtenemos los datos correctamente
                if(obtenerDatos()){
                    //Creamos un nuevo estudiante
                    p = new Profesor(nombre, ciclo, despacho, edad, curso);

                    //Abrimos bbdd y insertamos estudiante
                    abrirBD();

                    adaptador.insertarProfesor(p);

                    //Mostramos mensaje de éxito
                    Toast.makeText(getApplicationContext(), "Profesor añadido con éxito", Toast.LENGTH_SHORT).show();

                    //Cerramos activity para volver al MainActivity
                    finish();
                }
                else{
                    //Decimos que no se ha podido añadir
                    Toast.makeText(getApplicationContext(), "Error. Algún dato es incorrecto.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private boolean obtenerDatos(){

        try{
            nombre = String.valueOf(cajaNombre.getText());
            ciclo = String.valueOf(cajaCiclo.getText());
            edad = Integer.parseInt(String.valueOf(cajaEdad.getText()));
            curso = Integer.parseInt(String.valueOf(cajaCurso.getText()));
            despacho = String.valueOf(cajaDespacho.getText());
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    private void abrirBD(){
        //Creamos el objeto del adaptador
        adaptador = new Adaptador_DB(this);
        //Abrimos
        adaptador.abrir();
    }
}

