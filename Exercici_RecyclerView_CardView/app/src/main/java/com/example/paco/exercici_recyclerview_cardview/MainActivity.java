package com.example.paco.exercici_recyclerview_cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Creamos arraylist con 4 restaurantes
    private ArrayList<Restaurant> listaRestaurantes;
    private RecyclerView rv;
    private RecyclerView.LayoutManager rvLM;
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaRestaurantes = new ArrayList<Restaurant>();

        //Rellenamos la lista de restaurantes que queremos mostrar creando manualmente los datos
        listaRestaurantes.add(new Restaurant("Restaurante 1", "Direccion 1", (float) 300.30 , (float) 4.0, R.mipmap.restaurante1));
        listaRestaurantes.add(new Restaurant("Restaurante 2", "Direccion 2", (float) 150.00, (float) 1.5, R.mipmap.restaurente2));
        listaRestaurantes.add(new Restaurant("Restaurante 3", "Direccion 3", (float) 230.52, (float) 4.5, R.mipmap.restaurente3));
        listaRestaurantes.add(new Restaurant("Restaurante 4", "Direccion 4", (float) 30.60, (float) 3.5, R.mipmap.restaurante4));

        //Recuperamos recyclerview del layout
        rv = (RecyclerView)findViewById(R.id.recyclerView);

        //Triem el LayoutManager que volem utilitzar i l'assignem a l'objecte recyclerView
        rvLM = new LinearLayoutManager(this);
        rv.setLayoutManager(rvLM);

        //Creem l'adaptador que interactuarà amb les dades
        adaptador = new Adaptador(listaRestaurantes);

        //Enllacem el RecyclerView amb l'adaptador
        rv.setAdapter(adaptador);
}
}
