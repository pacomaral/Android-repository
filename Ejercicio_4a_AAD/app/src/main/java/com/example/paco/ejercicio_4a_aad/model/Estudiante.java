package com.example.paco.ejercicio_4a_aad.model;

import android.content.ContentValues;

/**
 * Created by Paco on 29/11/2017.
 */

public class Estudiante {

    //ATRIBUTOS
    private String nombre, ciclo;
    private int edad, curso;
    private float notaMedia;

    //CONSTRUCTOR
    public Estudiante(String nombre, String ciclo, int edad, int curso, float notaMedia) {
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.edad = edad;
        this.curso = curso;
        this.notaMedia = notaMedia;
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

    public float getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(float notaMedia) {
        this.notaMedia = notaMedia;
    }

    public ContentValues crearContentValues(){
        //Registro de valores para insertar
        ContentValues datosEstudiante = new ContentValues();

        //Asignamos valores
        datosEstudiante.put("nombre_estudiante", nombre);
        datosEstudiante.put("edad_estudiante", edad);
        datosEstudiante.put("ciclo_estudiante", ciclo);
        datosEstudiante.put("curso_estudiante", curso);
        datosEstudiante.put("nota_media_estudiante", notaMedia);

        return datosEstudiante;
    }
}
