package com.example.paco.exercici_recyclerview_cardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paco on 28/11/2017.
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderRestaurantes> {

    //Lista con los elementos que queremos mostrar
    private List<Restaurant> restaurantes;

    public Adaptador(ArrayList listaRestaurantes) {
        this.restaurantes = listaRestaurantes;
    }

    //Esta clase interna tiene que ser estática y extender de ViewHolder
    public static class ViewHolderRestaurantes extends RecyclerView.ViewHolder {

        //Elementos a mostrar de cada elemento
        private TextView textoNombre, textoDireccion, textoPrecio;
        private RatingBar barraEstrellas;
        private ImageView imagen;

        //Constructor
        public ViewHolderRestaurantes(View v) {
            super(v);

            //Recuperamos elementos del layout de cada elemento (elemento.xml)
            textoNombre = (TextView) v.findViewById(R.id.textoNombre);
            textoDireccion = (TextView) v.findViewById(R.id.textoDireccion);
            textoPrecio = (TextView) v.findViewById(R.id.textoPrecio);
            barraEstrellas = (RatingBar) v.findViewById(R.id.barraEstrellas);
            imagen = (ImageView) v.findViewById(R.id.imageView);
        }
    }

    @Override
    public ViewHolderRestaurantes onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflamos el layout de cada elemento
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento, parent, false);

        //Devolvemos un objeto del ViewHolder con el View creada de parámetro
        return new ViewHolderRestaurantes(v);
    }

    @Override

    public void onBindViewHolder(ViewHolderRestaurantes holder, int posicion) {

        //Aquí asignaremos las propiedades correspondientes a los Views correspondientes
        holder.textoNombre.setText(restaurantes.get(posicion).getNombre());
        holder.textoDireccion.setText(restaurantes.get(posicion).getDireccion());
        holder.textoPrecio.setText(String.valueOf(restaurantes.get(posicion).getPrecio() + " €"));
        holder.barraEstrellas.setRating(restaurantes.get(posicion).getPuntuacion());
        holder.imagen.setImageResource(restaurantes.get(posicion).getImagen());
    }

    @Override
    public int getItemCount() {
        //Devolveremos el tamaño de la lista de restaurantes a mostrar
        return restaurantes.size();
    }
}
