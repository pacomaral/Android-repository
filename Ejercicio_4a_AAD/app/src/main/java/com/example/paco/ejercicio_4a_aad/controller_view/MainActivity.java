package com.example.paco.ejercicio_4a_aad.controller_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paco.ejercicio_4a_aad.R;
import com.example.paco.ejercicio_4a_aad.model.Adaptador_DB;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button insertarEstudiante, insertarProfesor, borrarEstudiante, borrarProfesor, borrarBd, hacerConsulta, insertarAsignatura;
    private EditText cajaIdEstudiante, cajaIdProfesor;
    private Adaptador_DB adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Recogemos elementos del layout
        insertarEstudiante = (Button) findViewById(R.id.botonInsertarEstudiante);
        insertarProfesor = (Button) findViewById(R.id.botonInsertarProfesor);
        borrarEstudiante = (Button) findViewById(R.id.botonBorrarEstudiante);
        borrarProfesor = (Button) findViewById(R.id.botonBorrarProfesor);
        borrarBd = (Button) findViewById(R.id.botonBorrarBd);
        cajaIdEstudiante = (EditText)findViewById(R.id.cajaIdEstudiante);
        cajaIdProfesor = (EditText)findViewById(R.id.cajaIdProfesor);
        hacerConsulta = (Button)findViewById(R.id.botonHacerConsulta);
        insertarAsignatura = (Button)findViewById(R.id.botonInsertarAsignatura);


        //Añadimos listener a los botones
        insertarEstudiante.setOnClickListener(this);
        insertarProfesor.setOnClickListener(this);
        borrarEstudiante.setOnClickListener(this);
        borrarProfesor.setOnClickListener(this);
        borrarBd.setOnClickListener(this);
        hacerConsulta.setOnClickListener(this);
        insertarAsignatura.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.botonInsertarEstudiante){
            //Abriremos el subactivity de insertar estudiante
            Intent i = new Intent(this, Activity_Estudiante.class);
            startActivityForResult(i, 1);
        }
        else if(v.getId() == R.id.botonInsertarProfesor){
            //Abriremos el subactivity de insertar profesor
            Intent i = new Intent(this, Activity_Profesor.class);
            startActivityForResult(i, 1);
        }
        else if(v.getId() == R.id.botonInsertarAsignatura){
            //Abriremos el activity de insertar asignatura -- No utilizamos startActivityForResult ya que no esperamos nada
            Intent i = new Intent(getApplicationContext(), Activity_Asignatura.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.botonBorrarEstudiante){
                    //Borraremos estudiante según Id
                    adaptador = new Adaptador_DB(getApplicationContext());
                    adaptador.abrir();

                    try {//Si se logra borrar el estudiante, mostramos mensaje
                        if (adaptador.borrarEstudiante(Integer.valueOf(String.valueOf(cajaIdEstudiante.getText())))) {
                    Toast.makeText(getApplicationContext(), "Estudiante borrado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. No existe ningún estudiante con esa ID", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e){
                //Si no se han introducido datos válidos, lo indicamos
                Toast.makeText(getApplicationContext(), "ID no válida", Toast.LENGTH_SHORT).show();
            }

            adaptador.cerrar();
        }
        else if(v.getId() == R.id.botonBorrarProfesor){
            //Borraremos profesor según Id
            adaptador = new Adaptador_DB(getApplicationContext());
            adaptador.abrir();

            try {//Si se logra borrar el estudiante, mostramos mensaje
                if (adaptador.borrarProfesor(Integer.valueOf(String.valueOf(cajaIdProfesor.getText())))) {
                    Toast.makeText(getApplicationContext(), "Profesor borrado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. No existe ningún profesor con esa ID", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e){
                //Si no se han introducido datos válidos, lo indicamos
                Toast.makeText(getApplicationContext(), "ID no válida", Toast.LENGTH_SHORT).show();
            }

            adaptador.cerrar();
        }
        else if(v.getId() == R.id.botonBorrarBd){
            //Borraremos la base de datos completamente
            adaptador = new Adaptador_DB(getApplicationContext());
            if(adaptador.borrarBD(getApplicationContext(), "db_ej4a.db")){
                Toast.makeText(getApplicationContext(), "Base de datos borrada", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId() == R.id.botonHacerConsulta){
            //Abriremos el activity de las consultas
            Intent i = new Intent(getApplicationContext(), Activity_Consultas.class);
            startActivity(i);
        }
    }
}
