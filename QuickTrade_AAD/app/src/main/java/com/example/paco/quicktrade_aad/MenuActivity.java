package com.example.paco.quicktrade_aad;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paco.quicktrade_aad.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button botonAnyadirProducto, botonMisProductos, botonVerProductos;
    private Button botonModificarPerfil, botonVerUsuarios, botonCerrarSesion;
    private TextView textoUsuario;

    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Recuperamos elementos del layout
        botonAnyadirProducto = (Button)findViewById(R.id.botonAnyadirProducto);
        botonMisProductos = (Button)findViewById(R.id.botonMisProductos);
        botonVerProductos = (Button)findViewById(R.id.botonVerProductos);
        botonModificarPerfil = (Button)findViewById(R.id.botonModificarPerfil);
        botonVerUsuarios = (Button)findViewById(R.id.botonVerUsuarios);
        botonCerrarSesion = (Button)findViewById(R.id.botonCerrarSesion);
        textoUsuario = (TextView)findViewById(R.id.textoNombreUsuario);

        //Añadimos listener
        botonAnyadirProducto.setOnClickListener(this);
        botonMisProductos.setOnClickListener(this);
        botonVerProductos.setOnClickListener(this);
        botonModificarPerfil.setOnClickListener(this);
        botonVerUsuarios.setOnClickListener(this);
        botonCerrarSesion.setOnClickListener(this);


        //Recuperamos el usuario activo desde el intent que lanzó la actividad
        Intent i = getIntent();
        Bundle parametros = i.getExtras();

        nombreUsuario = parametros.getString("usuario");

        //Lo ponemos en el textview
        textoUsuario.setText(nombreUsuario);



    }

    //Método onClick general
    public void onClick(View v) {

        //Comprobaremos sobre qué botón se ha hecho click
        if(v.getId() == R.id.botonAnyadirProducto){
            //Lanzaremos NewProductActivity
            //Lanzaremos ProductsActivity -- Pasandole el nombre de usuario
            Intent i = new Intent(getApplicationContext(), NewProductActivity.class);

            //Pasaremos en el intent el nombre del usuario con la sesión activa
            Bundle parametros = new Bundle();
            parametros.putString("usuario", nombreUsuario);

            i.putExtras(parametros);

            startActivity(i);

        }
        else if(v.getId() == R.id.botonMisProductos){
            //Lanzaremos ProductsActivity -- Filtrando por el nombre de usuario logueado
            Intent i = new Intent(getApplicationContext(), ProductsActivity.class);

            //Pasaremos en el intent el nombre del usuario con la sesión activa
            Bundle parametros = new Bundle();
            parametros.putString("usuario", nombreUsuario);
            parametros.putBoolean("filtrar", true);            //Filtraremos por usuario

            i.putExtras(parametros);

            startActivity(i);
        }
        else if(v.getId() == R.id.botonVerProductos){
            //Lanzaremos ProductsActivity
            Intent i = new Intent(getApplicationContext(), ProductsActivity.class);

            //Pasaremos en el intent el nombre del usuario con la sesión activa
            Bundle parametros = new Bundle();
            parametros.putString("usuario", nombreUsuario);
            parametros.putBoolean("filtrar", false);            //En este caso no filtraremos por usuario

            i.putExtras(parametros);

            startActivity(i);
        }
        else if(v.getId() == R.id.botonModificarPerfil){
            //Lanzaremos ProfileActivity

        }
        else if(v.getId() == R.id.botonVerUsuarios){
            //Lanzaremos UsersActivity


        }
        else if(v.getId() == R.id.botonCerrarSesion){
            //Volveremos a abrir el MainActivity (logIn)
            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            //Cerraremos actividad para no poder volver atrás
            finish();

            //No pasaremos ningún parámetro
            startActivity(i);
        }
    }
}
