package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.example.paco.quicktrade_aad.model.Producto;
import com.example.paco.quicktrade_aad.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener{

    //Elementos del layout
    private EditText cajaNombre, cajaDescripcion, cajaPrecio;
    private RadioButton opcionHogar, opcionTecnologia, opcionCoches;
    private Button botonNuevoProducto, botonElegirImagen;

    //Datos para crear producto
    private String nombreUsuario, nombreProducto, descripcionProducto, precioProducto, categoriaProducto, idImagen;

    private DBControl dbControl;
    private String categoria;

    //Variables necesarias para modificar en vez de registrar nuevo
    private String nombreProdMod;
    private String nombreUserMod;
    private boolean editar = false;
    private Producto p;

    //Constante necesaria para cuando haya que elegir una imagen de la galería
    private static final int IMAGE_REQUEST = 1;
    private boolean imagenElegida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        //Recogemos elementos del layout
        cajaNombre = (EditText)findViewById(R.id.cajaNombreProducto);
        cajaDescripcion = (EditText)findViewById(R.id.cajaDescripcionProducto);
        cajaPrecio = (EditText)findViewById(R.id.cajaPrecioProducto);
        opcionHogar = (RadioButton)findViewById(R.id.opcionHogar);
        opcionTecnologia = (RadioButton)findViewById(R.id.opcionTecnologia);
        opcionCoches = (RadioButton)findViewById(R.id.opcionCoches);
        botonNuevoProducto = (Button)findViewById(R.id.botonNuevoProducto);
        botonElegirImagen = (Button)findViewById(R.id.botonElegirImagen);

        dbControl = new DBControl(this);

        //Intentamos recoger datos del intent
        try{
            Intent i = getIntent();
            Bundle b = i.getExtras();

            nombreProdMod = b.getString("nombre");
            editar = b.getBoolean("editar");
            nombreUserMod = b.getString("usuario");
        }
        catch(Exception e){
            editar = false;
        }

        botonElegirImagen.setOnClickListener(this);

        //Si hay que modificar el producto en lugar de registrarlo
        if(editar){

            //En caso de editar, ponemos booleano en true porque ya tendrá imagen
            imagenElegida = true;

            botonNuevoProducto.setText("Actualizar datos");

            //Rellenamos las cajas con los datos del producto
            consultarInfo(nombreProdMod, nombreUserMod);

            //Añadimos nuevo listener al boton -- No el de la activity
            botonNuevoProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(datosCorrectos()){
                        //Recogemos datos de las cajas
                        recogerDatos();

                        //Actualizamos el producto
                        actualizarInfo(nombreProdMod,nombreUserMod);

                        Toast.makeText(getApplicationContext(), "Producto modificado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //En caso de querer registrar uno nuevo
        else {
            //Añadimos listener al botón
            botonNuevoProducto.setOnClickListener(this);

            //Tendremos que recoger del intent que abrió la actividad, el nombre del usuario activo
            Intent i = getIntent();
            Bundle parametros = i.getExtras();
            nombreUsuario = parametros.getString("usuario");

        }


    }

    //Listener onClick
    public void onClick(View v) {
        if(v.getId() == R.id.botonNuevoProducto) {
            if (datosCorrectos()) {
                //Recogemos los datos de las cajas si están correctos
                recogerDatos();

                //Creamos el objeto Producto con los datos recogidos
                Producto p = new Producto(nombreProducto, descripcionProducto, categoriaProducto, precioProducto, nombreUsuario, idImagen);

                //Lo añadimos en la BBDD
                dbControl.anyadirProductoBD(p);

                //Mostramos mensaje de éxito
                Toast.makeText(getApplicationContext(), "Producto añadido con éxito", Toast.LENGTH_SHORT).show();

                //Volveremos a la activity anterior
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Falta algún dato por introducir", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId() == R.id.botonElegirImagen){
            //Lanzaremos con un intent un activity para elegir imagen de la galería
            Intent i = new Intent();
            i.setType("image/*");                        //Para que sólo podamos elegir imágenes
            i.setAction(Intent.ACTION_GET_CONTENT);      //Con esto se nos abrirá la app indicada para elegir las imágenes

            startActivityForResult(Intent.createChooser(i, "Elije una imagen"), IMAGE_REQUEST);
        }
    }

    //Método que se llamará al elegir una imagen
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Si el código es el que esperamos
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {

            //Recuperamos la imagen en un File - Uri
            Uri file = data.getData();

            //Subimos este objeto File a firebase storage
            dbControl.guardarImagen(file);

            //Guardamos idImagen
            idImagen = file.getLastPathSegment();

            imagenElegida = true;
        }
    }

    //Método para comprobar si los datos introducidos son correctos
    public boolean datosCorrectos(){

        if(cajaNombre.getText().toString().isEmpty() || cajaDescripcion.getText().toString().isEmpty() || cajaPrecio.getText().toString().isEmpty()){
           return false;
        }
        else{
            if(!opcionCoches.isChecked() && !opcionHogar.isChecked() && !opcionTecnologia.isChecked()){
                return false;
            }
            else if(!imagenElegida){
                Toast.makeText(getApplicationContext(), "No has elegido una imagen", Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                //Recogemos categoría según opción elegida
                if(opcionCoches.isChecked()){
                    categoria = "Coches";
                }
                else if(opcionTecnologia.isChecked()){
                    categoria = "Tecnología";
                }
                else if(opcionHogar.isChecked()){
                    categoria = "Hogar";
                }
                return true;
            }
        }


    }

    public void recogerDatos(){
        nombreProducto = cajaNombre.getText().toString();
        descripcionProducto = cajaDescripcion.getText().toString();
        precioProducto = cajaPrecio.getText().toString();
        categoriaProducto = categoria;
    }

    //MÉTODOS NECESARIOS PARA MODIFICAR PRODUCTO EN LUGAR DE REGISTRARLO

    private void consultarInfo(String nombreProd, final String nombreUsuario){
        DatabaseReference dbr = dbControl.getReferenceProduct();

        Query q=dbr.orderByChild("nombre").equalTo(nombreProd);              //No funciona si se utiliza R.string.usuario

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //De cada nodo producto con ese nombre, comprobamos el usuario
                    p = datasnapshot.getValue(Producto.class);

                    if(p.getUsuario().equals(nombreUsuario)){
                        //Rellenamos cajas con los datos del producto
                        cajaNombre.setText(p.getNombre());
                        cajaDescripcion.setText(p.getDescripcion());
                        cajaPrecio.setText(p.getPrecio());
                        if(p.getCategoria().equals("Coches")){
                            opcionCoches.setChecked(true);
                            opcionTecnologia.setChecked(false);
                            opcionHogar.setChecked(false);
                        }
                        else if(p.getCategoria().equals("Tecnología")){
                            opcionTecnologia.setChecked(true);
                            opcionHogar.setChecked(false);
                            opcionCoches.setChecked(false);
                        }
                        else if(p.getCategoria().equals("Hogar")){
                            opcionHogar.setChecked(true);
                            opcionCoches.setChecked(false);
                            opcionTecnologia.setChecked(false);
                        }
                        idImagen = p.getIdImagen();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void actualizarInfo(String nombre, final String user){

        final DatabaseReference dbr = dbControl.getReferenceProduct();

        Query q=dbr.orderByChild("nombre").equalTo(nombre);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    //Obtenemos cada nodo Producto obtenido
                    p = datasnapshot.getValue(Producto.class);

                    //Comprobamos que sea del usuario correcto
                    if(p.getUsuario().equals(user)){
                        //Actualizamos los datos de este nodo
                        String clave=datasnapshot.getKey();

                        dbr.child(clave).child("nombre").setValue(nombreProducto);
                        dbr.child(clave).child("categoria").setValue(categoriaProducto);
                        dbr.child(clave).child("precio").setValue(precioProducto);
                        dbr.child(clave).child("descripcion").setValue(descripcionProducto);
                        dbr.child(clave).child("idImagen").setValue(idImagen);
                    }


                }

                //En este momento, finalizaremos esta actividad para volver a la anterior (ProductsActivity)
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
