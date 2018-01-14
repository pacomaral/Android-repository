package com.example.paco.quicktrade_aad;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private String nombreUsuario, nombre, apellidos, direccion, correo;

    private ImageButton botonVolver;
    private TextView textoUsuario, textoNombre, textoCorreo, textoDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //Recogemos elementos del layout
        botonVolver = (ImageButton)findViewById(R.id.botonBackInfo);
        textoNombre = (TextView)findViewById(R.id.txtNombreInfo);
        textoCorreo = (TextView)findViewById(R.id.txtCorreoInfo);
        textoDireccion = (TextView)findViewById(R.id.txtDireccionInfo);
        textoUsuario = (TextView)findViewById(R.id.txtApodoInfo);


        //Recogeremos la información del usuario del Intent que abrió activity
        Intent i = getIntent();
        Bundle b = i.getExtras();

        nombreUsuario = b.getString("usuario");
        nombre = b.getString("nombre");
        apellidos = b.getString("apellidos");
        correo = b.getString("correo");
        direccion = b.getString("direccion");

        //Mostraremos los datos en los correspondientes elementos
        mostrarInfo();

        botonVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Al presionar el botón, cerraremos la actividad para volver al Activity anterior
        finish();
    }

    private void mostrarInfo(){
        textoNombre.setText(nombre + " " + apellidos);
        textoDireccion.setText(direccion);
        textoCorreo.setText(correo);
        textoUsuario.setText("Perfil de usuario: "+nombreUsuario);
    }
}
