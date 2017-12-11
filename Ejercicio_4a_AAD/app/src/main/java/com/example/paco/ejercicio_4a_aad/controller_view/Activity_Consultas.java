package com.example.paco.ejercicio_4a_aad.controller_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private int posicionElemento;
    private ArrayList<Integer> listaIds;

    //Semáforos que utilizaremos para saber si el listview tiene estudiantes o profesores
    private boolean mostrandoEstudiantes = false;
    private boolean mostrandoProfesores = false;

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


        registerForContextMenu(listaElementos);
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

            mostrandoEstudiantes = true;
            mostrandoProfesores = false;
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

            mostrandoProfesores = true;
            mostrandoEstudiantes = false;
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

            mostrandoProfesores = true;
            mostrandoEstudiantes = true;
        }

        //Obtenemos lista de Ids obtenidos en la consulta
        listaIds = adaptador.getIds();
    }

    private void actualizarListView(ArrayList<String> elementos){

        //Creamos arrayAdapter -- Le pasamos layout modificado de parámetro para que los elementos sean negros
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.elemento_negro_listview, R.id.elemento_lista, elementos);
        //Asignamos el adaptador al ListView
        listaElementos.setAdapter(adaptador);
    }

    //Método que configurará e inflará el contextmenu que tenemos creado en el recurso menú
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        //Con esto obtendremos  info sobre el elemento del listview se ha hecho click
        AdapterView.AdapterContextMenuInfo informacion = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //Obtenemos la posicion de este elemento
        posicionElemento = informacion.position;

        //Inflamos el menú
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    //Listener para los elementos del listview (tendran contextmenu)
    public boolean onContextItemSelected(MenuItem item) {


        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.opcionEliminar:


                adaptador = new Adaptador_DB(getApplicationContext());
                //Borraremos el registro
                adaptador.abrir();

                //Si hemos hecho consulta de estudiantes
                if(mostrandoEstudiantes && !mostrandoProfesores) {
                    if (adaptador.borrarEstudiante(listaIds.get(posicionElemento))) {
                        //Borramos elemento de la lista y actualizamos listview
                        elementos.remove(posicionElemento);
                        actualizarListView(elementos);
                        //Mostramos mensaje de éxito
                        Toast.makeText(getApplicationContext(), "Se ha borrado el estudiante con ID: " + listaIds.get(posicionElemento), Toast.LENGTH_SHORT).show();
                        adaptador.cerrar();

                        posicionElemento = -1;
                    } else {
                        //Mostramos mensaje de éxito
                        Toast.makeText(getApplicationContext(), "No se ha podido borrar el registro", Toast.LENGTH_SHORT).show();
                    }
                }

                //Si hemos hecho consulta de profesores
                else if(mostrandoProfesores && !mostrandoEstudiantes) {
                    if (adaptador.borrarProfesor(listaIds.get(posicionElemento))) {
                        //Borramos elemento de la lista y actualizamos listview
                        elementos.remove(posicionElemento);
                        actualizarListView(elementos);
                        //Mostramos mensaje de éxito
                        Toast.makeText(getApplicationContext(), "Se ha borrado el profesor con ID: " + listaIds.get(posicionElemento), Toast.LENGTH_SHORT).show();
                        adaptador.cerrar();

                        posicionElemento = -1;
                    } else {
                        //Mostramos mensaje de éxito
                        Toast.makeText(getApplicationContext(), "No se ha podido borrar el registro", Toast.LENGTH_SHORT).show();
                    }
                }

                //Si hemos mostrado ambos
                else if(mostrandoProfesores && mostrandoEstudiantes){
                    //Si es un estudiante, borramos estudiante
                    if(elementos.get(posicionElemento).contains("Estudiante")){
                        if (adaptador.borrarEstudiante(listaIds.get(posicionElemento))) {
                            //Borramos elemento de la lista y actualizamos listview
                            elementos.remove(posicionElemento);
                            actualizarListView(elementos);
                            //Mostramos mensaje de éxito
                            Toast.makeText(getApplicationContext(), "Se ha borrado el estudiante con ID: " + listaIds.get(posicionElemento), Toast.LENGTH_SHORT).show();
                            adaptador.cerrar();

                            posicionElemento = -1;
                        } else {
                            //Mostramos mensaje de éxito
                            Toast.makeText(getApplicationContext(), "No se ha podido borrar el registro", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Si es un profesor, borramos profesor
                    else if(elementos.get(posicionElemento).contains("Profesor")){
                        if (adaptador.borrarProfesor(listaIds.get(posicionElemento))) {
                            //Borramos elemento de la lista y actualizamos listview
                            elementos.remove(posicionElemento);
                            actualizarListView(elementos);
                            //Mostramos mensaje de éxito
                            Toast.makeText(getApplicationContext(), "Se ha borrado el profesor con ID: " + listaIds.get(posicionElemento), Toast.LENGTH_SHORT).show();
                            adaptador.cerrar();

                            posicionElemento = -1;
                        } else {
                            //Mostramos mensaje de éxito
                            Toast.makeText(getApplicationContext(), "No se ha podido borrar el registro", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
