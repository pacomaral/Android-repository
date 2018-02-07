package com.example.paco.pacomaravillaaliaga_examen_2av.Model;

import com.example.paco.pacomaravillaaliaga_examen_2av.R;

/**
 * Created by Paco on 30/01/2018.
 */

public class Curso {

    //Variables
    private String curso, titulacion, fecha;
    private int numAlumnos, imagen;

    //Constructor
    public Curso(String curso, String titulacion, String fecha, int numAlumnos) {
        this.curso = curso;
        this.titulacion = titulacion;
        this.fecha = fecha;
        this.numAlumnos = numAlumnos;

        //Según curso / titulación, el icono será uno u otro
        if((curso + titulacion).equals("2nDAM")){
            this.imagen = R.drawable.disappointment;
        }
        else if((curso + titulacion).equals("1rDAM")){
            this.imagen = R.drawable.sad;
        }
        else if((curso + titulacion).equals("2nDAW")){
            this.imagen = R.drawable.anger;
        }
        else if((curso + titulacion).equals("1rDAW")){
            this.imagen = R.drawable.cute;
        }
    }

    //Getters y setters
    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumAlumnos() {
        return numAlumnos;
    }

    public void setNumAlumnos(int numAlumnos) {
        this.numAlumnos = numAlumnos;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
