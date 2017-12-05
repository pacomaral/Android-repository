package com.example.paco.exercici_recyclerview_cardview;

/**
 * Created by Paco
 */

public class Restaurant {

    //Atributos necesarios
    private String nombre, direccion;
    private float puntuacion, precio;
    private int imagen;


    /**
     * Constructor
     */
    public Restaurant(String nombre, String direccion, float precio, float puntuacion, int imagen){
        this.nombre=nombre;
        this.direccion=direccion;
        this.precio=precio;
        this.puntuacion=puntuacion;
        this.imagen=imagen;
    }

    /*
     * Getters y Setters
     */
    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
