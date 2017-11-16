package com.example.paco.pasandoparametros;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText cajaNombre;
    private Button botonEnviar;
    private RadioGroup opciones;
    private RadioButton opcion1, opcion2;
    private TextView textoInfo;
    private static final int PETICION = 1;
    private CharSequence cadenaEdad, nombre;
    private int edad = 19;
    private boolean segundaVez = false;                     //Variable para comprobar si se ha introducido ya la edad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos elementos del xml a los objetos Java creados previamente en esta clase
        cajaNombre=(EditText)findViewById(R.id.cajaNombre);
        botonEnviar=(Button)findViewById(R.id.botonEnviar);
        textoInfo=(TextView)findViewById(R.id.textoInfo);
        opcion1=(RadioButton)findViewById(R.id.opcionHombre);
        opcion2=(RadioButton)findViewById(R.id.opcionMujer);
        opciones=(RadioGroup)findViewById(R.id.grupo1);

        //Añadimos un listener al boton enviar
        botonEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Creamos un intent (no ponemos this porque sería el contexto del listener)
                Intent i = new Intent(getApplicationContext(), Activity_2.class);
                //Con un objeto bundle pasaremos información a otra actividad
                Bundle parametrosAPasar = new Bundle();
                parametrosAPasar.putCharSequence("nombre", cajaNombre.getText());
                //Añadimos el bundle al Intent
                i.putExtras(parametrosAPasar);
                //Lanzamos la actividad 2
                startActivityForResult(i, PETICION);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datos) {

        super.onActivityResult(requestCode, resultCode, datos);

        if(requestCode == PETICION){
            switch (resultCode) {
                case RESULT_OK:
                    Bundle parametrosRecibidos = datos.getExtras();
                    if(parametrosRecibidos != null){
                        cadenaEdad = parametrosRecibidos.getCharSequence("Edad");
                        //Pasamos la cadena de la edad a entero
                        edad = Integer.parseInt(cadenaEdad.toString());
                    }
                    break;
                case RESULT_CANCELED:
                    // Cancelación o cualquier situación de error
                    break;
            }
        }

        //Ponemos el semáforo en true
        segundaVez = true;                  //ARREGLAR

        //Desactivamos boton y caja de texto
        botonEnviar.setEnabled(false);
        cajaNombre.setEnabled(false);
        opcion1.setEnabled(false);
        opcion2.setEnabled(false);


        //Finalmente mostramos la información según la edad
        if(edad > 18 && edad < 25){
            textoInfo.setText("Como tienes " + edad + " años, ya eres mayor de edad");
        }
        else if(edad >=25 && edad < 35){
            textoInfo.setText("Tienes " + edad + " años, estás en la flor de la vida");
        }
        else if(edad >= 35){
            textoInfo.setText("Tienes " + edad + " años, tienes que empezar a cuidarte..");
        }
        else{
            textoInfo.setText("Tienes " + edad + " años, eres menor de edad");
        }
    }

    public void onSaveInstanceState(Bundle b){
        if(b != null) {
            //Si el usuario ya ha introducido su edad, guardamos la información que muestra el TextView
            if (segundaVez) {
                b.putCharSequence("textoInfo", textoInfo.getText());
                b.putInt("edad", edad);
            }
            //De todas maneras guardaremos el nombre del usuario y qué radiobutton está seleccionado
            b.putCharSequence("nombre", cajaNombre.getText());

            if (opcion1.isChecked()) {
                b.putCharSequence("sexo", "masculino");
            } else if (opcion2.isChecked()) {
                b.putCharSequence("sexo", "femenino");
            }
        }
    }

    public void onRestoreInstanceState(Bundle b){
        if(b != null) {
            if(b.containsKey("edad")){
                //Recuperamos la información del usuario
                textoInfo.setText(b.getCharSequence("textoInfo"));
                //Recuperamos la edad
                edad = b.getInt("edad");
                //Desactivamos los campos innecesarios
                botonEnviar.setEnabled(false);
                cajaNombre.setEnabled(false);
                opcion1.setEnabled(false);
                opcion2.setEnabled(false);
            }
            cajaNombre.setText(b.getCharSequence("nombre"));
            if (b.getCharSequence("sexo").toString().equals("masculino")) {
                opcion1.setChecked(true);
                opcion2.setChecked(false);
            } else {
                opcion2.setChecked(true);
                opcion1.setChecked(false);
            }
        }
    }
}
