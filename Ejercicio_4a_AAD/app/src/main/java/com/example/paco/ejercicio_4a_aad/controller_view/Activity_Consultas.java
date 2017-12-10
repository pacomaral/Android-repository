package com.example.paco.ejercicio_4a_aad.controller_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paco.ejercicio_4a_aad.R;
import com.example.paco.ejercicio_4a_aad.model.Adaptador_DB;

import java.util.ArrayList;

public class Activity_Consultas extends AppCompatActivity implements View.OnClickListener{

    private Button botonRecuperarTodos, botonRecuperarEstudiantes, botonRecuperarProfesores;
    private EditText cajaCiclo, cajaCurso;
    private ListView listaElementos;
    private ArrayList<String> elementos;
    private Adaptador_DB adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__consultas);

        //Recuperamos elementos del layout
        botonRecuperarEstudiantes = (Button)findViewById(R.id.botonRecuperarEstudiantes);
        botonRecuperarProfesores = (Button)findViewById(R.id.botonRecuperarProfesores);
        botonRecuperarTodos = (Button)findViewById(R.id.botonRecuperarTodos);
        listaElementos = (ListView)findViewById(R.id.listViewResultado);
        cajaCiclo = (EditText)findViewById(R.id.cajaConsultaCiclo);
        cajaCurso = (EditText)findViewById(R.id.cajaConsultaCurso);

        //Añadimos listener
        botonRecuperarEstudiantes.setOnClickListener(this);
        botonRecuperarProfesores.setOnClickListener(this);
        botonRecuperarTodos.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        adaptador = new Adaptador_DB(getApplicationContext());

        if(v.getId() == R.id.botonRecuperarEstudiantes){

            adaptador.abrir();

            if(String.valueOf(cajaCiclo.getText()).isEmpty() && String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperar todos los estudiantes
                elementos = adaptador.obtenerEstudiantes(null, 0);
            }
            else if(!String.valueOf(cajaCiclo.getText()).isEmpty() && String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperamos estudiantes segun ciclo
                elementos = adaptador.obtenerEstudiantes(String.valueOf(cajaCiclo.getText()), 0);
            }
            else if(String.valueOf(cajaCiclo.getText()).isEmpty() && !String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperamos estudiantes segun curso
                elementos = adaptador.obtenerEstudiantes(null, Integer.parseInt(String.valueOf(cajaCurso.getText())));
            }
            else if(!String.valueOf(cajaCiclo.getText()).isEmpty() && !String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperamos estudiantes según ciclo y según curso
                elementos = adaptador.obtenerEstudiantes(String.valueOf(cajaCiclo.getText()), Integer.valueOf(String.valueOf(cajaCurso.getText())));
            }

            //Si el arrayList está vacío, indicamos que no se han encontrado coincidencias
            if(elementos.isEmpty()){
                Toast.makeText(getApplicationContext(), "No se han encontrado coincidencias", Toast.LENGTH_SHORT).show();
            }

            //Actualizamos el listview
            actualizarListView(elementos);

            //Cerramos bd
            adaptador.cerrar();
        }
        else if(v.getId() == R.id.botonRecuperarProfesores){

            adaptador.abrir();

            if(String.valueOf(cajaCiclo.getText()).isEmpty() && String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperar todos los profesores
                elementos = adaptador.obtenerProfesores(null, 0);

            }
            else if(!String.valueOf(cajaCiclo.getText()).isEmpty() && String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperamos profesores segun ciclo
                elementos = adaptador.obtenerProfesores(String.valueOf(cajaCiclo.getText()), 0);
            }
            else if(String.valueOf(cajaCiclo.getText()).isEmpty() && !String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperamos profesores segun curso
                elementos = adaptador.obtenerProfesores(null, Integer.parseInt(String.valueOf(cajaCurso.getText())));
            }
            else if(!String.valueOf(cajaCiclo.getText()).isEmpty() && !String.valueOf(cajaCurso.getText()).isEmpty()){
                //Recuperamos profesores según ciclo y según curso
                elementos = adaptador.obtenerProfesores(String.valueOf(cajaCiclo.getText()), Integer.valueOf(String.valueOf(cajaCurso.getText())));
            }

            //Actualizamos el listview
            actualizarListView(elementos);

            //Si el arrayList está vacío, indicamos que no se han encontrado coincidencias
            if(elementos.isEmpty()){
                Toast.makeText(getApplicationContext(), "No se han encontrado coincidencias", Toast.LENGTH_SHORT).show();
            }

            adaptador.cerrar();
        }
        else if(v.getId() == R.id.botonRecuperarTodos){

            adaptador.abrir();

            //Recuperaremos estudiantes y profesores
            elementos = adaptador.obtenerTodos();
            //Actualizamos el listview
            actualizarListView(elementos);

            //Si no se obtienen registros, significará que está vacía
            if(elementos.isEmpty()){
                Toast.makeText(getApplicationContext(), "La base de datos está vacía", Toast.LENGTH_SHORT).show();
            }

            adaptador.cerrar();
        }
    }

    private void actualizarListView(ArrayList<String> elementos){

        //Creamos arrayAdapter -- Le pasamos layout modificado de parámetro para que los elementos sean negros
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.elemento_negro_listview, R.id.elemento_lista, elementos);
        //Asignamos el adaptador al ListView
        listaElementos.setAdapter(adaptador);
    }
}
