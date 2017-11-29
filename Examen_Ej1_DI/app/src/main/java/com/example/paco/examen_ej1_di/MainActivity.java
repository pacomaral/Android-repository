package com.example.paco.examen_ej1_di;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox checkBox;
    private RadioButton opcionFondoNegro, opcionFondoVerde, opcionFondoRojo;
    private RadioButton opcionTextoBlanco, opcionTextoAmarillo, opcionTextoAzul;
    private TextView textoPrueba;
    private LinearLayout layoutTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperamos elementos del layout para manipularlos
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        opcionFondoNegro = (RadioButton)findViewById(R.id.opcionFondoNegro);
        opcionFondoVerde = (RadioButton)findViewById(R.id.opcionFondoVerde);
        opcionFondoRojo = (RadioButton)findViewById(R.id.opcionFondoRojo);
        opcionTextoAmarillo = (RadioButton)findViewById(R.id.opcionTextoAmarillo);
        opcionTextoAzul = (RadioButton)findViewById(R.id.opcionTextoAzul);
        opcionTextoBlanco = (RadioButton)findViewById(R.id.opcionTextoBlanco);
        textoPrueba = (TextView)findViewById(R.id.textoPrueba);
        layoutTexto = (LinearLayout)findViewById(R.id.layout0);

        //Escondemos el texto en un principio
        textoPrueba.setText("");


        //--------------------------------------------------------
        //LISTENERS / EVENTOS NECESARIOS
        //--------------------------------------------------------

        //Listener para checkBox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    textoPrueba.setText("Texto de prueba");
                }
                else{
                    textoPrueba.setText("");
                }
            }
        });

        opcionTextoAmarillo.setOnClickListener(this);
        opcionTextoBlanco.setOnClickListener(this);
        opcionTextoAzul.setOnClickListener(this);
        opcionFondoNegro.setOnClickListener(this);
        opcionFondoVerde.setOnClickListener(this);
        opcionFondoRojo.setOnClickListener(this);
    }

    //Para controlar los clicks en radiobuttons
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.opcionTextoAmarillo || v.getId() == R.id.opcionTextoAzul || v.getId() == R.id.opcionTextoBlanco) {
            //Controlamos que radibuttons están seleccionados (solo uno de cada grupo podrá estarlo)
            if (opcionTextoAmarillo.isChecked()) {
                textoPrueba.setTextColor(Color.YELLOW);
            } else if (opcionTextoAzul.isChecked()) {
                textoPrueba.setTextColor(Color.BLUE);
            } else if (opcionTextoBlanco.isChecked()) {
                textoPrueba.setTextColor(Color.WHITE);
            }
        }
        else {
            if (opcionFondoRojo.isChecked()) {
                textoPrueba.setBackgroundColor(Color.RED);
            } else if (opcionFondoVerde.isChecked()) {
                textoPrueba.setBackgroundColor(Color.GREEN);
            } else if (opcionFondoNegro.isChecked()) {
                textoPrueba.setBackgroundColor(Color.BLACK);
            }
        }

    }
}
