package com.example.paco.pacomaravillaaliaga_examen_2av.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.paco.pacomaravillaaliaga_examen_2av.Model.Curso;
import com.example.paco.pacomaravillaaliaga_examen_2av.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paco on 30/01/2018.
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderElementos> {


    //Listado de cursos con los que trabajará el recyclerView
    private List<Curso> listaCursos;

    //Constructor -- Le pasaremos la lista de cursos que queremos mostrar
    public Adaptador(ArrayList<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    //Esta clase interna tiene que ser estática y extender de ViewHolder
    public static class ViewHolderElementos extends RecyclerView.ViewHolder{

        //Elementos a mostrar de cada elemento
        private TextView textoCurso, textoFecha, textoNumAlumnos;       //textoCurso = curso + titulación
        private ImageView imagen;

        //Constructor
        public ViewHolderElementos(View v) {
            super(v);

            //Recuperamos elementos del layout de cada elemento (elemento.xml)
            textoCurso = (TextView)v.findViewById(R.id.txtCurso);
            textoFecha = (TextView)v.findViewById(R.id.txtFecha);
            textoNumAlumnos = (TextView)v.findViewById(R.id.txtNumAlumnos);
            imagen = (ImageView)v.findViewById(R.id.imagenElemento);


        }

    }


    @Override
    public Adaptador.ViewHolderElementos onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflamos el layout de cada elemento
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento, parent, false);

        //Devolvemos un objeto del ViewHolder con el View creada de parámetro
        return new ViewHolderElementos(v);
    }

    @Override
    public void onBindViewHolder(Adaptador.ViewHolderElementos holder, int posicion) {

        //Aquí asignaremos las propiedades correspondientes a los Views correspondientes
        holder.textoCurso.setText(listaCursos.get(posicion).getCurso() + "      " + listaCursos.get(posicion).getTitulacion());
        holder.textoFecha.setText("Curs: " + listaCursos.get(posicion).getFecha());
        holder.textoNumAlumnos.setText(listaCursos.get(posicion).getNumAlumnos() + " alumnes");
        holder.imagen.setImageResource(listaCursos.get(posicion).getImagen());
    }

    @Override
    public int getItemCount() {

        //Devolveremos el tamaño de la lista de cursos a mostrar
        return listaCursos.size();
    }
}
