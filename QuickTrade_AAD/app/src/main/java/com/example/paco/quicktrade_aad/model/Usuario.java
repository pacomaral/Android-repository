package com.example.paco.quicktrade_aad.model;

import java.util.ArrayList;

/**
 * Created by Paco on 07/01/2018.
 */

public class Usuario {


    private String correo, usuario, nombre, apellidos, direccion;

    private ArrayList<String> listaProductosFavoritos;

    //Constructor - El vacío es necesario para que funcione Firebase
    public Usuario(){

    }

    public ArrayList<String> getListaProductosFavoritos() {
        return listaProductosFavoritos;
    }

    public void setListaProductosFavoritos(ArrayList<String> listaProductosFavoritos) {
        this.listaProductosFavoritos = listaProductosFavoritos;
    }

    public Usuario(String correo, String usuario, String nombre, String apellidos, String direccion, ArrayList<String> listaProductosFavoritos) {
        this.correo = correo;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.listaProductosFavoritos = listaProductosFavoritos;

    }

    //Getters y setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
