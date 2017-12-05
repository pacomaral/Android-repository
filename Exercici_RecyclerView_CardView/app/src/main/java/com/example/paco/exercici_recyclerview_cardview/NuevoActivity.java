package com.example.paco.exercici_recyclerview_cardview;

        import android.content.Intent;
        import android.media.Rating;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RatingBar;
        import android.widget.Toast;

public class NuevoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText cajaNombre, cajaDireccion, cajaPrecio;
    private RatingBar barraPuntuacion;
    private Button botonAnyadir;

    private String nombre, direccion;
    private float precio, puntuacion;
    private int imagen;                     //Utilizaremos siempre la misma imagen, solo es para probar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        //Recuperamos elementos del xml
        cajaNombre = (EditText)findViewById(R.id.cajaNombre);
        cajaDireccion = (EditText)findViewById(R.id.cajaDireccion);
        cajaPrecio = (EditText)findViewById(R.id.cajaPrecio);
        barraPuntuacion = (RatingBar)findViewById(R.id.barraPuntuacion);
        botonAnyadir = (Button)findViewById(R.id.botonAnyadirRestaurante);

        botonAnyadir.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        Bundle datos = getDatos();
        if(datos != null){
            //Tendremos que cerrar el activity, recoger datos y enviarlos al activity anterior
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtras(datos);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    //Método que devuelve un objeto Bundle con los datos del restaurante recogidos
    private Bundle getDatos(){
        Bundle b = new Bundle();
        try{
            //Recogemos los datos de las cajas
            nombre = String.valueOf(cajaNombre.getText());
            direccion = String.valueOf(cajaDireccion.getText());
            precio = Float.parseFloat(String.valueOf(cajaPrecio.getText()));
            puntuacion = barraPuntuacion.getRating();
            imagen = R.mipmap.restaurante1;                     //Utilizaremos siempre la misma imagen

            //Guardamos los datos en el bundle
            b.putString("nombre", nombre);
            b.putString("direccion", direccion);
            b.putFloat("precio", precio);
            b.putFloat("puntuacion", puntuacion);
            b.putInt("imagen", imagen);

            return b;
        }
        catch(Exception e){
            //Si no se pueden recoger es porque habrá alguno no válido
            Toast.makeText(getApplicationContext(), "Algún dato no es válido", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
