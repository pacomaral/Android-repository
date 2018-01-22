package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.example.paco.quicktrade_aad.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteProductsActivity extends AppCompatActivity {

    private ListView listViewProductos;
    private ArrayList<String> listaProductos;
    private ArrayAdapter<String> adaptadorListView;

    private String nombreUsuario;

    private DBControl dbControl;

    private Usuario u;
    private ArrayList<String> listaProdFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_products);

        dbControl = new DBControl(this);

        //Obtenemos elementos del layout
        listViewProductos = (ListView)findViewById(R.id.listViewProductosFavoritos);

        //Obtenemos nombre de usuario para buscar sus favoritos
        Intent i = getIntent();

        Bundle b = i.getExtras();

        nombreUsuario = b.getString("usuario");

        recuperarProductosFavoritos();
    }


    private void recuperarProductosFavoritos(){

        final DatabaseReference dbr = dbControl.getReferenceUser();

        Query q=dbr.orderByChild("usuario").equalTo(nombreUsuario);              //No funciona si se utiliza R.string.usuario

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //Cogemos el nodo usuario que se obtendrá
                    u = datasnapshot.getValue(Usuario.class);
                }

                //Obtenemos su lista de productos favoritos
                listaProdFavoritos = u.getListaProductosFavoritos();

                //Esta lista, será la que mostremos en el ArrayList, quitando el elemento con índice '0',
                //puesto que no coincide con ningún producto, sino que se rellena por defecto para crear el nodo
                listaProdFavoritos.remove(0);

                actualizarAdaptadorProductos(listaProdFavoritos);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void actualizarAdaptadorProductos(ArrayList<String> lista){

        //Adaptador -- Con layout definido por nosotros
        adaptadorListView = new ArrayAdapter<String>(getApplicationContext(), R.layout.elemento_listview, R.id.elemento_lista, lista);

        //Ponemos el adaptador al listView
        listViewProductos.setAdapter(adaptadorListView);
    }
}
