package com.example.paco.exercici_recyclerview_cardview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Creamos arraylist con 4 restaurantes
    private ArrayList<Restaurant> listaRestaurantes;
    private RecyclerView rv;
    private RecyclerView.LayoutManager rvLM;
    private Adaptador adaptador;
    private Button botonAnyadir;

    //Para el resultado de la actividad de añadir restaurante
    private static final int REQUEST = 1;

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

        //Recuperamos elementos del layout
        rv = (RecyclerView)findViewById(R.id.recyclerView);
        botonAnyadir = (Button)findViewById(R.id.botonAnyadir);


        //Triem el LayoutManager que volem utilitzar i l'assignem a l'objecte recyclerView - FALTA AÑADIR PARÁMETROS
        rvLM = new LinearLayoutManager(this);

        rv.setLayoutManager(rvLM);

        //Creem l'adaptador que interactuarà amb les dades
        adaptador = new Adaptador(listaRestaurantes);


        //Enllacem el RecyclerView amb l'adaptador
        rv.setAdapter(adaptador);


        //Añadimos listener
        botonAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NuevoActivity.class);

                startActivityForResult(i, REQUEST);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent datos) {

        super.onActivityResult(requestCode, resultCode, datos);

        if(requestCode == REQUEST){
            switch (resultCode) {
                case RESULT_OK:
                    Bundle b = datos.getExtras();
                    if(b != null){
                        //Obtenemos datos y creamos el restaurante
                        Restaurant res = new Restaurant(b.getString("nombre"), b.getString("direccion"), b.getFloat("precio"), b.getFloat("puntuacion"), b.getInt("imagen"));
                        //Añadimos un nuevo restaurante mediante el adaptador
                        adaptador.anyadirRestaurante(res);

                        Toast.makeText(getApplicationContext(), "Restaurante añadido", Toast.LENGTH_SHORT).show();

                        //Hacemos scroll hasta el nuevo restaurante
                        rv.scrollToPosition(adaptador.getItemCount()-1);
                    }
                    break;
                case RESULT_CANCELED:
                    // Cancelación o cualquier situación de error
                    break;
            }
        }
    }
}
