package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;


public class ProductInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private String nombre, descripcion, categoria, precio, idImagen;

    private ImageButton botonVolver;
    private TextView textoNombre, textoDescripcion, textoCategoria, textoPrecio;

    private ImageView imagen;

    private DBControl dbControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        dbControl = new DBControl(this);

        //Recogemos elementos del layout
        botonVolver = (ImageButton)findViewById(R.id.botonAtras);

        textoNombre = (TextView)findViewById(R.id.txtNombreProducto);
        textoDescripcion = (TextView)findViewById(R.id.txtDescripcion);
        textoCategoria = (TextView)findViewById(R.id.txtCategoria);
        textoPrecio = (TextView)findViewById(R.id.txtPrecio);
        imagen = (ImageView)findViewById(R.id.imagenProducto);


        //Recogeremos la información del producto del Intent que abrió activity
        Intent i = getIntent();
        Bundle b = i.getExtras();

        nombre = b.getString("nombre");
        descripcion = b.getString("descripcion");
        categoria = b.getString("categoria");
        precio = b.getString("precio");
        idImagen = b.getString("idImagen");

        //Mostraremos los datos en los correspondientes elementos
        mostrarInfo();

        //Mostramos la imagen correspondiente -- Con la biblioteca "Picasso"
        getImage(idImagen);

        botonVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Al presionar el botón, cerraremos la actividad para volver al Activity anterior
        finish();
    }

    private void mostrarInfo(){
        textoNombre.setText(nombre);
        textoDescripcion.setText(descripcion);
        textoPrecio.setText(precio);
        textoCategoria.setText(categoria);
    }

    private void getImage(String idImagen){


        FirebaseStorage storage = FirebaseStorage.getInstance();

        storage.getReference().child("imagenes/"+idImagen).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Ponemos la imagen en el ImageView
                Picasso.with(getApplicationContext()).load(uri).into(imagen);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No se ha podido recuperar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
