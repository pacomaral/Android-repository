package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.example.paco.quicktrade_aad.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private DBControl dbControl;

    private EditText cajaCorreo, cajaPassword, cajaUsuario, cajaNombre, cajaApellidos, cajaDireccion;
    private Button botonRegistrar;
    private TextView txt;

    private String correo, password, usuario, nombre, apellidos, direccion;
    private ArrayList<String> listaUsers;
    private int contador;

    private boolean editarUser = false;
    private String user;

    private Usuario u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Recuperamos elementos del layout
        cajaCorreo = (EditText)findViewById(R.id.cajaCorreoRegistro);
        cajaPassword = (EditText)findViewById(R.id.cajaPasswordRegistro);
        cajaUsuario = (EditText)findViewById(R.id.cajaUsuarioRegistro);
        cajaNombre = (EditText)findViewById(R.id.cajaNombreRegistro);
        cajaApellidos = (EditText)findViewById(R.id.cajaApellidosRegistro);
        cajaDireccion = (EditText)findViewById(R.id.cajaDireccionRegistro);
        botonRegistrar = (Button)findViewById(R.id.botonRegistrarse);
        txt = (TextView)findViewById(R.id.txtRegistro);

        dbControl = new DBControl(this);

        try{
            Intent i = getIntent();
            Bundle b = i.getExtras();

            editarUser = b.getBoolean("editar");
            user = b.getString("usuario");
        }
        catch(Exception e){
            //En caso de que no se pueda recoger datos del bundle
            editarUser = false;
        }


        //Comprobamos si será un nuevo registro, o será para editar la info
        if(editarUser){
            //Cambiamos el texto del TextView y del botón
            txt.setText("Editar perfil");
            botonRegistrar.setText("Actualizar");

            cajaPassword.setEnabled(false);
            cajaUsuario.setEnabled(false);

            //Rellenaremos las cajas con la info del usuario
            consultarInfo(user);

            //Añadimos listener modificado
            botonRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intentaremos actualizar la info
                    if(recogerDatosUsuario()){
                        actualizarInfo(user);
                        Toast.makeText(getApplicationContext(), "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Falta introducir algún dato", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {

            //Listener para el botón de registrar
            botonRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Si hemos podido recoger los datos del usuario
                    if (recogerDatosUsuario()) {
                        consultarUsuario(usuario);  //Este método se encargará de comprobar correo después, y si está correcto, registra al usuario
                    } else {
                        Toast.makeText(getApplicationContext(), "Falta introducir algún dato", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean recogerDatosUsuario(){
        //Si alguna caja está vacía, lo mostramos
        if(String.valueOf(cajaUsuario.getText()).isEmpty() || String.valueOf(cajaPassword.getText()).isEmpty() || String.valueOf(cajaApellidos.getText()).isEmpty() || String.valueOf(cajaCorreo.getText()).isEmpty() || String.valueOf(cajaDireccion.getText()).isEmpty() || String.valueOf(cajaNombre.getText()).isEmpty()){
            return false;
        }
        else{
            nombre = cajaNombre.getText().toString();
            correo = cajaCorreo.getText().toString();
            password = cajaPassword.getText().toString();
            usuario = cajaUsuario.getText().toString();
            apellidos = cajaApellidos.getText().toString();
            direccion = cajaDireccion.getText().toString();

            return true;
        }
    }

    private void consultarUsuario(String user){

        //-----------------------------------------------------------
        //PRIMERO COMPROBAMOS SI EXISTE ALGÚN USUARIO CON ESE NOMBRE
        DatabaseReference dbr = dbControl.getReferenceUser();
        //Obtenemos nodos que tienen el nombre de usuario igual al introducido por parámetro
        Query q = dbr.orderByChild("usuario").equalTo(user);    //No funciona si utilizo String.valueOf(R.string.campo_correo)


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int c = 0;
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    c++;
                    //Toast.makeText(getApplicationContext(), c + " es el contador", Toast.LENGTH_SHORT).show();
                }
                //Si hay algún usuario con ese nombre, mensaje
                if(c>0){
                    Toast.makeText(getApplicationContext(), "Ya existe un usuario con ese apodo", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Si no, comprobamos el correo
                    consultarCorreo(correo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //-----------------------------------------------------------
    }

    private void consultarCorreo(String correo){

        DatabaseReference dbr = dbControl.getReferenceUser();
        //Obtenemos nodos que tienen el correo igual al introducido por parámetro
        Query q = dbr.orderByChild("correo").equalTo(correo);   //No funciona si utilizo String.valueOf(R.string.campo_correo)


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int c = 0;
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    c++;
                    //Toast.makeText(getApplicationContext(), c + " es el contador", Toast.LENGTH_SHORT).show();
                }
                //Si el contador es mayor que 1, mostramos mensaje
                if(c>0){
                    Toast.makeText(getApplicationContext(), "Ya existe un usuario con ese correo", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Esta correcto, registramos usuario
                    registraUsuario();
                    Toast.makeText(getApplicationContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //-----------------------------------------------------------
    }

    private void registraUsuario(){
        //Registramos al usuario
        dbControl.registrarUsuario(correo, password);

        ArrayList<String> prodFavoritos = new ArrayList<String>();

        prodFavoritos.add("probando");


        //Creamos el usuario con todos los datos recogidos
        Usuario u = new Usuario(correo, usuario, nombre, apellidos, direccion, prodFavoritos);

        //Añadiremos el usuario a la BD
        dbControl.anyadirUsuarioBD(u);


        //Volvemos a la activity anterior
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //Con un objeto bundle pasaremos información a otra actividad
        Bundle parametrosAPasar = new Bundle();
        parametrosAPasar.putString("correo", correo);
        //Añadimos el bundle al Intent
        i.putExtras(parametrosAPasar);
        //Cerramos esta actividad
        setResult(RESULT_OK, i);
        finish();
    }


    //MÉTODOS NECESARIOS EN CASO DE NECESITAR ACTUALIZAR INFO DE USUARIO Y NO REGISTRARLO

    private void consultarInfo(String user){
        DatabaseReference dbr = dbControl.getReferenceUser();

        Query q=dbr.orderByChild("usuario").equalTo(user);              //No funciona si se utiliza R.string.usuario

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //Cogemos el nodo usuario que se obtendrá
                    u = datasnapshot.getValue(Usuario.class);
                }

                //Rellenamos cajas con los datos del usuario obtenido
                cajaNombre.setText(u.getNombre());
                cajaApellidos.setText(u.getApellidos());
                cajaCorreo.setText(u.getCorreo());
                cajaUsuario.setText(u.getUsuario());
                cajaDireccion.setText(u.getDireccion());
                cajaPassword.setText("******");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void actualizarInfo(String user){

        final DatabaseReference dbr = dbControl.getReferenceUser();

        Query q=dbr.orderByChild("usuario").equalTo(user);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                    //Cogemos la clave del nodo obtenido (será uno)
                    String clave=datasnapshot.getKey();

                    //Actualizamos todos los datos de este nodo
                    dbr.child(clave).child("nombre").setValue(nombre);
                    dbr.child(clave).child("apellidos").setValue(apellidos);
                    dbr.child(clave).child("direccion").setValue(direccion);
                    dbr.child(clave).child("correo").setValue(correo);
                }

                //En este momento, finalizaremos esta actividad para volver a la anterior (MenuActivity)
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
