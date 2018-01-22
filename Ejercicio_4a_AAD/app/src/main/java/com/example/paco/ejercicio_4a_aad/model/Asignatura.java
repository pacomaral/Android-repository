package com.example.paco.ejercicio_4a_aad.model;

import android.content.ContentValues;

/**
 * Created by Paco on 22/01/2018.
 */

public class Asignatura {

    private String nombre, horas;

    //Constructor
    public Asignatura(String nombre, String horas) {
        this.nombre = nombre;
        this.horas = horas;
    }

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    //Con esto agilizamos a la hora de insertar asignaturas en la BBDD
    public ContentValues crearContentValues(){
        //Registro de valores para insertar
        ContentValues datosAsignatura = new ContentValues();

        //Asignamos valores
        datosAsignatura.put("nombre_asignatura", nombre);
        datosAsignatura.put("horas_asignatura", horas);

        return datosAsignatura;
    }
}
