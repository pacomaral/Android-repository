package com.example.paco.exercici_recyclerview_cardview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
 * Created by Paco
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderRestaurantes> {

    //Lista con los elementos que queremos mostrar
    private List<Restaurant> restaurantes;

    public Adaptador(ArrayList<Restaurant> listaRestaurantes) {
        this.restaurantes = listaRestaurantes;
    }

    //Esta clase interna tiene que ser estática y extender de ViewHolder
    public static class ViewHolderRestaurantes extends RecyclerView.ViewHolder {

        //Elementos a mostrar de cada elemento
        private TextView textoNombre, textoDireccion, textoPrecio;
        private RatingBar barraEstrellas;
        private ImageView imagen;

        //Necesario para listener
        public View v;

        //Constructor
        public ViewHolderRestaurantes(View v) {
            super(v);

            //Recuperamos elementos del layout de cada elemento (elemento.xml)
            textoNombre = (TextView) v.findViewById(R.id.textoNombre);
            textoDireccion = (TextView) v.findViewById(R.id.textoDireccion);
            textoPrecio = (TextView) v.findViewById(R.id.textoPrecio);
            barraEstrellas = (RatingBar) v.findViewById(R.id.barraEstrellas);
            imagen = (ImageView) v.findViewById(R.id.imageView);

            //Necesario para listener
            this.v = v;
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

        //Necesario para listener
        final int pos = posicion;

        //Aquí asignaremos las propiedades correspondientes a los Views correspondientes
        holder.textoNombre.setText(restaurantes.get(posicion).getNombre());
        holder.textoDireccion.setText(restaurantes.get(posicion).getDireccion());
        holder.textoPrecio.setText(String.valueOf(restaurantes.get(posicion).getPrecio() + " €"));
        holder.barraEstrellas.setRating(restaurantes.get(posicion).getPuntuacion());
        holder.imagen.setImageResource(restaurantes.get(posicion).getImagen());

        //Añadimos en onClickListener
        holder.v.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Mostramos el diálogo
                dialegEliminar(v, pos);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Devolveremos el tamaño de la lista de restaurantes a mostrar
        return restaurantes.size();
    }

    public void dialegEliminar(View v, final int pos){

        //v = elemento a eliminar
        //pos = posición que ocupa este elemento -> final para acceder desde clase interna onClick

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        String nombreElemento = restaurantes.get(pos).getNombre();
        String msg = "¿Deseas eliminar "+nombreElemento+"?";

        //Configuramos el mensaje que mostrará y el título
        builder.setMessage(msg);
        builder.setTitle("Eliminar elemento");

        //Configuramos las opciones si/no del diálogo
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restaurantes.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, getItemCount());

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //No haremos nada, solo se cerrará el diálogo
            }
        });

        //Creamos y mostramos el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Método para añadir un restaurante - Devolverá la posición en la que se ha añadido
    public void anyadirRestaurante(Restaurant res){

        //Añadiremos el restaurante al final de la lista
        restaurantes.add(res);
        //Notificaremos que un item ha sido añadido
        notifyItemInserted(restaurantes.size()-1);                  //size - 1 ya que la lista empieza del 0, y queremos la última posición
        //Notificamos para que cambie el rango
        notifyItemRangeChanged(restaurantes.size()-1, getItemCount());

    }
}
