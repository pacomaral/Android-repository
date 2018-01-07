package com.example.paco.quicktrade_aad.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.MainActivity;
import com.example.paco.quicktrade_aad.R;
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

    public DBControl(Context c){
        dbReferenceProd = FirebaseDatabase.getInstance().getReference("productos");
        dbReferenceUser = FirebaseDatabase.getInstance().getReference("usuarios");
        context = c;
        comunicacion = (interfazDBControl) c;
    }

    /*
     *   Métodos
     */

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
                            //Toast.makeText(context, "Usuario logueado con éxito", Toast.LENGTH_SHORT).show();
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

    public boolean existeUsuario(String user){

        //Obtenemos nodos que tienen el nombre de usuario igual al introducido por parámetro
        Query q = dbReferenceUser.orderByChild(String.valueOf(R.string.campo_usuario)).equalTo(user);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    sumaContador();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Si hay más de 0 usuarios con ese nombre, sí que existe el usuario
        if(contador > 0){
            //Limpiamos el contador
            contador = 0;
            return true;
        }
        else{
            //Limpiamos el contador
            contador = 0;
            return false;
        }


    }

    public boolean existeCorreo(String correo){

        //Obtenemos nodos que tienen el correo introducido por parámetro
        Query q = dbReferenceUser.orderByChild(String.valueOf(R.string.campo_correo)).equalTo(correo);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    sumaContador();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Si hay más de 0 usuarios con ese nombre, sí que existe el usuario
        if(contador > 0){
            //Limpiamos el contador
            contador = 0;
            return true;
        }
        else{
            //Limpiamos el contador
            contador = 0;
            return false;
        }
    }

    private void sumaContador(){
        contador++;
    }


    public interface interfazDBControl{
        void loginCorrecto();
        void loginIncorrecto();
    }
}
