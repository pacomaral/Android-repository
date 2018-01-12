package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.example.paco.quicktrade_aad.model.Producto;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener{

    //Elementos del layout
    private EditText cajaNombre, cajaDescripcion, cajaPrecio;
    private RadioButton opcionHogar, opcionTecnologia, opcionCoches;
    private Button botonNuevoProducto;

    //Datos para crear producto
    private String nombreUsuario, nombreProducto, descripcionProducto, precioProducto, categoriaProducto;

    private DBControl dbControl;
    private String categoria;

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

        //Añadimos listener al botón
        botonNuevoProducto.setOnClickListener(this);

        //Tendremos que recoger del intent que abrió la actividad, el nombre del usuario activo
        Intent i = getIntent();
        Bundle parametros = i.getExtras();
        nombreUsuario = parametros.getString("usuario");

        //Inicializamos el objeto que utilizaremos para añadir el producto
        dbControl = new DBControl(this);

    }

    //Listener onClick
    public void onClick(View v) {
        if(datosCorrectos()){
            //Recogemos los datos de las cajas si están correctos
            recogerDatos();

            //Creamos el objeto Producto con los datos recogidos
            Producto p = new Producto(nombreProducto, descripcionProducto, categoriaProducto, precioProducto, nombreUsuario);

            //Lo añadimos en la BBDD
            dbControl.anyadirProductoBD(p);

            //Mostramos mensaje de éxito
            Toast.makeText(getApplicationContext(), "Producto añadido con éxito", Toast.LENGTH_SHORT).show();

            //Volveremos a la activity anterior
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Falta algún dato por introducir", Toast.LENGTH_SHORT).show();
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

}
