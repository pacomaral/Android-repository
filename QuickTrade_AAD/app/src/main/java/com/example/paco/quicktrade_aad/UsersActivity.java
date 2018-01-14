package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.example.paco.quicktrade_aad.model.Producto;
import com.example.paco.quicktrade_aad.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {


    private DBControl dbControl;

    private ListView listViewUsuarios;

    private ArrayAdapter<String> adaptadorListViewUsers;
    private ArrayList<String> listaUsuarios;

    private Usuario user;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        listaUsuarios = new ArrayList<String>();
        dbControl = new DBControl(this);

        //Recuperamos elementos del layout
        listViewUsuarios = (ListView)findViewById(R.id.listViewUsuarios);

        obtenerUsuarios();

        //Le asignamos el contextMenu al list view
        registerForContextMenu(listViewUsuarios);
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
        inflater.inflate(R.menu.menu_listview_users, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {

        //Con esto obtendremos el nombre de usuario seleccionado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        nombreUsuario = listaUsuarios.get(info.position);

        switch (item.getItemId()) {
            case R.id.opcionVerPerfil:
                //Recogeremos todos los datos del usuario, y abriremos UserInfoActivity
                lanzarActivityConInfo(nombreUsuario);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     *  Otros Métodos
     */

    //Con esto obtendremos los nombres de todos los usuarios que aparecerán en el ListView
    private void obtenerUsuarios(){

        //Limpiamos la lista por si ya tenía elementos
        listaUsuarios.clear();

        //Obtenemos la referencia al nodo productos de la BD
        DatabaseReference dbr = dbControl.getReferenceUser();

        //Añadimos este controlador para obtener los usuarios y que se actualice cuando haya algún cambio
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //De cada nodo producto, obtenemos un objeto de este
                    Usuario u = datasnapshot.getValue(Usuario.class);
                    //Obtenemos su nombre de usuario
                    String nombre = u.getUsuario();
                    //Lo añadimos a la lista
                    listaUsuarios.add(nombre);
                }

                //Creamos adaptador y lo asignamos al listview
                adaptadorListViewUsers = new ArrayAdapter<String>(getApplicationContext(), R.layout.elemento_listview, R.id.elemento_lista, listaUsuarios);
                listViewUsuarios.setAdapter(adaptadorListViewUsers);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Este método se encargará de recoger datos necesarios del usuario y lanzar la nueva actividad
    private void lanzarActivityConInfo(String usuario){


        DatabaseReference dbr = dbControl.getReferenceUser();

        Query q=dbr.orderByChild("usuario").equalTo(usuario);              //No funciona si se utiliza R.string.usuario

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //Cogemos el nodo usuario que se obtendrá
                    user = datasnapshot.getValue(Usuario.class);
                }

                //Abriremos la nueva Activity con la info del usuario obtenido
                Intent i = new Intent(getApplicationContext(), UserInfoActivity.class);
                Bundle b = new Bundle();

                b.putString("usuario", user.getUsuario());
                b.putString("nombre", user.getNombre());
                b.putString("apellidos", user.getApellidos());
                b.putString("direccion", user.getDireccion());
                b.putString("correo", user.getCorreo());

                i.putExtras(b);

                startActivity(i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
