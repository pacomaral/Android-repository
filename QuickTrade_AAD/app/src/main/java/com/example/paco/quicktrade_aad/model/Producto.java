package com.example.paco.quicktrade_aad.model;

/**
 * Created by Paco on 07/01/2018.
 */

public class Producto {


    private String nombre, descripcion, categoria, precio, usuario;


    /**
     *  Constructor
     */

    //Este constructor vacío necesita estar aquí para que Firebase funcione correctamente
    public Producto(){

    }

    public Producto(String nombre, String descripcion, String categoria, String precio, String usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.usuario = usuario;
    }

    /*
     *  Getters y setters
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
