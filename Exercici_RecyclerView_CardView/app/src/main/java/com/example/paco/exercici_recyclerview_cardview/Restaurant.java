package com.example.paco.exercici_recyclerview_cardview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Paco
 */

public class Restaurant implements Parcelable {

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

    protected Restaurant(Parcel in) {
        imagen = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imagen);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}