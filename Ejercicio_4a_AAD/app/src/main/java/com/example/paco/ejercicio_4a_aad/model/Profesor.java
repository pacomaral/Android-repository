package com.example.paco.ejercicio_4a_aad.model;

import android.content.ContentValues;

/**
 * Created by Paco on 29/11/2017.
 */

public class Profesor {

    //ATRIBUTOS
    private String nombre, ciclo, despacho;
    private int edad, curso;

    //CONSTRUCTOR
    public Profesor(String nombre, String ciclo, String despacho, int edad, int curso) {
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.despacho = despacho;
        this.edad = edad;
        this.curso = curso;
    }

    //GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getDespacho() {
        return despacho;
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public ContentValues crearContentValues(){

        //Registro de valores para insertar
        ContentValues datosProfesor = new ContentValues();

        //Asignamos valores
        datosProfesor.put("nombre_profesor", nombre);
        datosProfesor.put("edad_profesor", edad);
        datosProfesor.put("ciclo_profesor", ciclo);
        datosProfesor.put("curso_profesor", curso);
        datosProfesor.put("despacho_profesor", despacho);

        return datosProfesor;
    }
}
