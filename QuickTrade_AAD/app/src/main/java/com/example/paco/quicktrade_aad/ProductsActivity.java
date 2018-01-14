package com.example.paco.quicktrade_aad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.example.paco.quicktrade_aad.model.Producto;
import com.example.paco.quicktrade_aad.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner spinnerCategoria, spinnerUsuarios;
    private ImageButton botonMenu;
    private ListView listViewProductos;

    private ArrayList<String> listaProductos;
    private ArrayList<String> listaCategoria;
    private ArrayList<String> listaUsuarios;

    private ArrayAdapter<String> adaptadorListView;
    private ArrayAdapter<String> adaptadorSpinner;
    private ArrayAdapter<String> adaptadorSpinnerCategoria;

    private DBControl dbControl;

    private boolean filtrar;
    private String nombreUsuario;

    //Para recoger datos
    private Bundle datosRecibidos;
    private Intent intent;

    private Context ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        ref = this;

        //Recuperamos elementos del layout
        spinnerCategoria = (Spinner)findViewById(R.id.spinnerCategoria);
        spinnerUsuarios = (Spinner)findViewById(R.id.spinnerUsuario);
        botonMenu = (ImageButton)findViewById(R.id.botonMenuIcon);
        listViewProductos = (ListView)findViewById(R.id.listViewProductos);

        //Creamos objeto para gestionar la base de datos
        dbControl = new DBControl(this);

        //Inicializamos listas
        listaUsuarios = new ArrayList<String>();
        listaCategoria = new ArrayList<String>();
        listaProductos = new ArrayList<String>();

        //Comprobaremos si deberemos filtrar por usuario o no dependiendo de los datos recibidos con el intent
        intent = getIntent();
        datosRecibidos = intent.getExtras();

        nombreUsuario = datosRecibidos.getString("usuario");

        filtrar = datosRecibidos.getBoolean("filtrar");

        if(filtrar){

            //El spinner de usuarios esta desactivado -- Querremos mostrar en este el nombre de usuario
            listaUsuarios.clear();

            listaUsuarios.add(nombreUsuario);

            //Creamos el adaptador para el spinner
            adaptadorSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaUsuarios);
            spinnerUsuarios.setAdapter(adaptadorSpinner);

            spinnerUsuarios.setEnabled(false);

            getProductosDeUsuario(nombreUsuario);
        }
        else{
            getProductos();
            getUsuarios();
        }

        //Al iniciar la actividad, rellenamos spinner de categorías
        mostrarCategorias();

        //Añadimos listener
        botonMenu.setOnClickListener(this);

        //Registramos el contextMenu al listview
        registerForContextMenu(listViewProductos);
    }

    /**
     *  Métodos para el contextmenu
     */
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        //Inflamos el menú
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listview_products, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {

        //Con esto obtendremos el nombre del producto seleccionado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        String nombreProducto = listaProductos.get(info.position);

        switch (item.getItemId()) {
            case R.id.opcionModificarProducto:
                validarUsuario_Modificar(nombreProducto);
                return true;
            case R.id.opcionBorrarProducto:
                //modificarProducto(String nombre);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     *  Otros Métodos
     */

    private void mostrarCategorias(){

        //Rellenamos la lista con las 3 categorías
        listaCategoria.add("---");
        listaCategoria.add("Tecnología");
        listaCategoria.add("Coches");
        listaCategoria.add("Hogar");

        //Creamos el adaptador para el ListView
        adaptadorSpinnerCategoria = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaCategoria);
        spinnerCategoria.setAdapter(adaptadorSpinnerCategoria);
    }


    private void getProductos(){

        //Obtenemos la referencia al nodo productos de la BD
        DatabaseReference dbr = dbControl.getReferenceProduct();

        //Añadimos este controlador para obtener los productos y que se actualice cuando haya algún cambio
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //Antes que nada, limpiamos la lista por si tenía elementos
                    listaProductos.clear();
                    //De cada nodo producto, obtenemos un objeto de este
                    Producto p = datasnapshot.getValue(Producto.class);
                    //Obtenemos su nombre
                    String nombre = p.getNombre();
                    //Lo añadimos a la lista
                    listaProductos.add(nombre);
                }

                //Creamos el adaptador para el ListView
                actualizarAdaptadorProductos(listaProductos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getProductosDeUsuario(String usuario){

        DatabaseReference dbr = dbControl.getReferenceProduct();

        Query q=dbr.orderByChild("usuario").equalTo(usuario);              //No funciona si se utiliza R.string.producto_usuario

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpiamos la lista por si tenía elementos
                listaProductos.clear();
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //De cada nodo producto, obtenemos un objeto de este
                    Producto p = datasnapshot.getValue(Producto.class);
                    //Obtenemos su nombre
                    String nombre = p.getNombre();
                    //Lo añadimos a la lista
                    listaProductos.add(nombre);
                }

                //Creamos el adaptador para el ListView
                actualizarAdaptadorProductos(listaProductos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUsuarios(){

        //Limpiamos la lista
        listaUsuarios.clear();

        //Obtenemos la referencia al nodo productos de la BD
        DatabaseReference dbr = dbControl.getReferenceUser();

        //Añadimos este controlador para obtener los productos y que se actualice cuando haya algún cambio
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //Este será el primer elemento de la lista
                listaUsuarios.add("---");

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //De cada nodo producto, obtenemos un objeto de este
                    Usuario u = datasnapshot.getValue(Usuario.class);
                    //Obtenemos su nombre de usuario
                    String nombre = u.getUsuario();
                    //Lo añadimos a la lista
                    listaUsuarios.add(nombre);
                }

                //Creamos el adaptador para el ListView
                adaptadorSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaUsuarios);
                spinnerUsuarios.setAdapter(adaptadorSpinner);
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

    @Override
    public void onClick(View v) {

        //Finalizamos la actividad -- Volverá a MenuActivity ya que no fue finalizada
        finish();
    }

    private void validarUsuario_Modificar(final String nombreProducto){

        DatabaseReference dbr = dbControl.getReferenceProduct();

        Query q = dbr.orderByChild("nombre").equalTo(nombreProducto);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //Obtenemos el nodo producto
                    Producto p = datasnapshot.getValue(Producto.class);

                    //Comprobamos si el usuario propietario es el activo
                    if(p.getUsuario().equals(nombreUsuario)){
                        //Lanzaremos NewProductActivity pasando datos correctos
                        Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                        Bundle b = new Bundle();

                        b.putString("usuario", nombreUsuario);
                        b.putString("nombre", nombreProducto);
                        b.putBoolean("editar", true);

                        i.putExtras(b);

                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Este producto no es tuyo", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
