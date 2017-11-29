package com.example.paco.pacomaravillaaliagaexamen;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_2 extends AppCompatActivity {

    private TextView textoNombre, textoEdad, textoSexo, textoPuntuacion, textoLecturas;
    private Button botonCerrar;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //Recuperamos elementos del layout
        textoNombre = (TextView) findViewById(R.id.txtNombre);
        textoEdad = (TextView) findViewById(R.id.txtEdad);
        textoLecturas = (TextView) findViewById(R.id.txtLectures);
        textoPuntuacion = (TextView) findViewById(R.id.txtPuntuacion);
        textoSexo = (TextView) findViewById(R.id.txtSexe);
        botonCerrar = (Button) findViewById(R.id.botonCerrarVentana);

        mainActivity = new MainActivity();


        //Cogemos el intent que ha lanzado este subactivity
        Intent i = getIntent();
        Bundle parametrosRecibidos = i.getExtras();

        //Ponemos en los textview los parámetros recibidos
        if(parametrosRecibidos != null){
            textoNombre.setText((CharSequence) parametrosRecibidos.get("nombre"));
            textoEdad.setText((CharSequence) parametrosRecibidos.get("edad"));
            textoLecturas.setText((CharSequence) parametrosRecibidos.get("lecturas"));
            textoSexo.setText((CharSequence) parametrosRecibidos.get("sexo"));
            textoPuntuacion.setText((CharSequence) String.valueOf(parametrosRecibidos.get("puntuacion")));
        }

        //Evento para el boton
        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cerraremos este subactivity


                //Antes que nada eliminamos el fragment dinámico

                //mainActivity.eliminarFragmentDinamico();  -- No funciona, per lo que no s'eliminara el fragment dinàmic

                //Cerramos
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                //Cerramos esta actividad
                setResult(RESULT_OK, intent);
                finish();


            }
        });
    }
}
