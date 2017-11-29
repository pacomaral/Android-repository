package com.example.paco.pacomaravillaaliagaexamen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DadesPersonalFragment.interfazFragmentDades, AficionsFragment.interfazFragmentAficions {

    private String nombre;
    private String sexo;
    private String edad;
    private String lecturas;
    private float puntuacion;

    private static final int PETICION = 1;

    private ArrayList<Object> listaInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos arraylist
        listaInfo = new ArrayList<Object>();
    }

    @Override
    public void enviarDatos(String nombre, String edad, String sexo) {
        Fragment fragmentDades = getFragmentManager().findFragmentById(R.id.espaiFragment);
        if(fragmentDades != null){
            //Recuperamos datos introducidos en el fragmento
            this.nombre = nombre;
            this.edad = edad;
            this.sexo = sexo;
        }
    }

    @Override
    public void guardarAficiones(String lecturas, float puntuacion) {
        Fragment fragmentAficiones = getFragmentManager().findFragmentById(R.id.espaiFragment);
        if(fragmentAficiones != null){
            //Recuperamos datos
            this.lecturas = lecturas;
            this.puntuacion = puntuacion;
        }
    }

    public ArrayList<Object> pasarInfo(){
        listaInfo.add(nombre);
        listaInfo.add(edad);
        listaInfo.add(sexo);
        listaInfo.add(lecturas);
        listaInfo.add(puntuacion);

        return listaInfo;
    }

    public void lanzarSubActivity(){
        //Creamos un intent (no ponemos this porque sería el contexto del listener)
        Intent i = new Intent(getApplicationContext(), Activity_2.class);

        //Con un objeto bundle pasaremos información a otra actividad
        Bundle parametrosAPasar = new Bundle();

        //Recogemos los datos (sabemos en que posiciones están)
        parametrosAPasar.putString("nombre", this.nombre);
        parametrosAPasar.putString("edad", this.edad);
        parametrosAPasar.putString("sexo", this.sexo);
        parametrosAPasar.putString("lecturas", this.lecturas);
        parametrosAPasar.putFloat("puntuacion", this.puntuacion);

        //Añadimos el bundle al Intent
        i.putExtras(parametrosAPasar);

        //Lanzamos la actividad 2
        startActivityForResult(i, PETICION);
    }

    //No llegaremos a utilizar este método puesto que no queremos recoger nada del subactivity
    public void onActivityResult(int requestCode, int resultCode, Intent datos) {
        super.onActivityResult(requestCode, resultCode, datos);
    }

    //Método para eliminar el fragment dinámico desde el subactivity
    public void eliminarFragmentDinamico(){

        FragmentManager fm;
        FragmentTransaction ft;

        //Eliminamos el fragment dinámico que esté
        fm = getFragmentManager();
        ft = fm.beginTransaction();

        ft.remove(getFragmentManager().findFragmentById(R.id.espaiFragment));

        fm.popBackStack();
        fm.popBackStack();
    }



    //Para guardar datos antes de la rotación
    public void onSaveInstanceState(Bundle b){

        //Recogemos los datos (sabemos en que posiciones están)
        b.putString("nombre", this.nombre);
        b.putString("edad", this.edad);
        b.putString("sexo", this.sexo);
        b.putString("lecturas", this.lecturas);
        b.putFloat("puntuacion", this.puntuacion);

        super.onSaveInstanceState(b);
    }

    //Para recuperar datos después de la rotación
    public void onRestoreInstanceState(Bundle b){

        super.onRestoreInstanceState(b);

        this.nombre = (String) b.get("nombre");
        this.edad = (String) b.get("edad");
        this.sexo = (String) b.get("sexo");
        this.lecturas = (String) b.get("lecturas");
        this.puntuacion = (Float) b.get("puntuacion");
    }

}
