package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.example.paco.quicktrade_aad.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private DBControl dbControl;

    private EditText cajaCorreo, cajaPassword, cajaUsuario, cajaNombre, cajaApellidos, cajaDireccion;
    private Button botonRegistrar;

    private String correo, password, usuario, nombre, apellidos, direccion;
    private ArrayList<String> listaUsers;
    private int contador;

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

        dbControl = new DBControl(this);


        //Listener para el botón de registrar
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si hemos podido recoger los datos del usuario
                if(recogerDatosUsuario()){
                    consultarUsuario(usuario);  //Este método se encargará de comprobar correo después, y si está correcto, registra al usuario
                }
                else{
                    Toast.makeText(getApplicationContext(), "Falta introducir algún dato", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        //Creamos el usuario con todos los datos recogidos
        Usuario u = new Usuario(correo, usuario, nombre, apellidos, direccion);

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

}
