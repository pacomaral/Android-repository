package com.example.paco.quicktrade_aad.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * Created by Paco on 07/01/2018.
 */

public class DBControl {

    private DatabaseReference dbReferenceProd, dbReferenceUser;
    private FirebaseAuth auth;
    private int contador = 0;

    private Context context;
    private interfazDBControl comunicacion;

    private String nombreUsuario;

    public DBControl(Context c){
        dbReferenceProd = FirebaseDatabase.getInstance().getReference("productos");
        dbReferenceUser = FirebaseDatabase.getInstance().getReference("usuarios");
        context = c;
    }

    /*
     *   Métodos
     */

    public DatabaseReference getReferenceProduct(){
        return dbReferenceProd;
    }

    public DatabaseReference getReferenceUser(){
        return dbReferenceUser;
    }

    public void registrarUsuario(String correo, String pass){
        //Obtenemos instancia
        auth = FirebaseAuth.getInstance();

        //Registramos al usuario
        auth.createUserWithEmailAndPassword(correo, pass).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //No queremos que se quede logueado el usuario, por lo que desautenticaremos
                            auth.signOut();

                            Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "No se ha podido registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void loginUsuario(String correo, String pass){
        //Obtenemos instancia
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Pasaremos el nombre de usuario al MainActivity
                            comunicacion.sendUsername(nombreUsuario);
                            //Comunicaremos al mainActivity mediante interfaz
                            comunicacion.loginCorrecto();
                        } else {
                            //Toast.makeText(context, "No se ha podido loguear el usuario", Toast.LENGTH_SHORT).show();
                            comunicacion.loginIncorrecto();
                        }
                    }
                });


    }

    public void anyadirUsuarioBD(Usuario u){

        //Obtenemos una clave
        String clave = dbReferenceUser.push().getKey();

        dbReferenceUser.child(clave).setValue(u);

    }

    public void anyadirProductoBD(Producto p){

        //Obtenemos una clave
        String clave = dbReferenceProd.push().getKey();

        dbReferenceProd.child(clave).setValue(p);
    }

    //Método para obtener el nombre de usuario a partir de su correo -- Necesitaremos para saber que usuario está logueado
    public void consultarNombreYLoguear(final String correo, final String pass){

        //Obtenemos nodos que tienen el correo igual al introducido por parámetro
        Query q = dbReferenceUser.orderByChild("correo").equalTo(correo);   //No funciona si utilizo String.valueOf(R.string.campo_correo)


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int c = 0;
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //Del nodo que obtenemos (será uno), obtenemos su nombre de usuario
                    Usuario u = datasnapshot.getValue(Usuario.class);
                    nombreUsuario = u.getUsuario();
                }

                //Una vez hemos recogido el nombre, ya llamamos al método loginUsuario
                loginUsuario(correo, pass);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    public interface interfazDBControl{
        void loginCorrecto();
        void loginIncorrecto();
        void sendUsername(String username);
    }

    public void getMainActivityContext(Context c){
        comunicacion = (interfazDBControl) c;
    }
}
