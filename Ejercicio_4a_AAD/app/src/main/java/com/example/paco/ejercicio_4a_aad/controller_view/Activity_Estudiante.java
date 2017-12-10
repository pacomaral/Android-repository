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

public class Activity_Estudiante extends AppCompatActivity {

    private EditText cajaNombre, cajaEdad, cajaCiclo, cajaCurso, cajaNota;
    private Button botonAnyadirEstudiante;
    private Adaptador_DB adaptador;
    private Estudiante e;

    private String nombre, ciclo;
    private int edad, curso;
    private String nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__estudiante);

        //Recuperamos elementos del layout
        cajaNombre = (EditText)findViewById(R.id.cajaNombreEstudiante);
        cajaEdad = (EditText)findViewById(R.id.cajaEdadEstudiante);
        cajaCiclo = (EditText)findViewById(R.id.cajaCicloEstudiante);
        cajaCurso = (EditText)findViewById(R.id.cajaCursoEstudiante);
        cajaNota = (EditText)findViewById(R.id.cajaNotaEstudiante);
        botonAnyadirEstudiante = (Button)findViewById(R.id.botonAnyadirEstudiante);

        //Añadimos listener al botón
        botonAnyadirEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Si obtenemos los datos correctamente
                if(obtenerDatos()){
                    //Creamos un nuevo estudiante
                    e = new Estudiante(nombre, ciclo, edad, curso, nota);

                    //Abrimos bbdd y insertamos estudiante
                    abrirBD();

                    adaptador.insertarEstudiante(e);

                    //Mostramos mensaje de éxito
                    Toast.makeText(getApplicationContext(), "Estudiante añadido con éxito", Toast.LENGTH_SHORT).show();

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
            nota = String.valueOf(cajaNota.getText());
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
