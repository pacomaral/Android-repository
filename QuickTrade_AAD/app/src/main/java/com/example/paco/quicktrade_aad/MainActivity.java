package com.example.paco.quicktrade_aad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paco.quicktrade_aad.model.DBControl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DBControl.interfazDBControl {


    private static final int PETICION = 1;                  //Constante a utilizar en el startActivityForResult

    private EditText cajaCorreo, cajaPassword;
    private Button botonAcceder, botonRegistrar;

    private boolean loginCorrecto = false;

    private DBControl dbControl;

    private String correo, nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recogemos elementos del layout
        cajaCorreo = (EditText) findViewById(R.id.cajaCorreoLogin);
        cajaPassword = (EditText) findViewById(R.id.cajaPasswordLogin);
        botonAcceder = (Button) findViewById(R.id.botonAccederLogin);
        botonRegistrar = (Button) findViewById(R.id.botonRegistroLogin);

        //Inicializamos objeto para realizar acciones en Firebase
        dbControl = new DBControl(this);

        //Añadimos listeners
        botonAcceder.setOnClickListener(this);
        botonRegistrar.setOnClickListener(this);

        //Le pasamos el contexto de esta actividad a DBControl para que se pueda comunicar
        dbControl.getMainActivityContext(this);

    }

    @Override
    public void onClick(View v) {

        //Si el click es sobre el botón de acceder
        if (v.getId() == R.id.botonAccederLogin) {
            if(!String.valueOf(cajaCorreo.getText()).isEmpty() && !String.valueOf(cajaPassword.getText()).isEmpty()){

                //Este método ya se encargará de comunicarse con esta actividad para saber si el usuario se ha logueado o no
                dbControl.consultarNombreYLoguear(String.valueOf(cajaCorreo.getText()), String.valueOf(cajaPassword.getText()));
            }
            else{
                Toast.makeText(getApplicationContext(), "Falta introducir correo o contraseña", Toast.LENGTH_SHORT).show();
            }

        }
        //Si el click es sobre el botón de registrarse
        else if (v.getId() == R.id.botonRegistroLogin) {
            //Lanzaremos SubActivity de registro
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivityForResult(i, PETICION);
        }
    }

    //Método que se lanzará al cerrar el SubActivity de registrarse
    public void onActivityResult(int requestCode, int resultCode, Intent datos) {

        super.onActivityResult(requestCode, resultCode, datos);

        if (requestCode == PETICION) {
            switch (resultCode) {
                case RESULT_OK:
                    Bundle parametrosRecibidos = datos.getExtras();
                    if (parametrosRecibidos != null) {
                        //Recogemos el correo con el que se ha registrado el usuario y lo ponemos en la caja correspondiente
                        correo = parametrosRecibidos.getString("correo");
                        cajaCorreo.setText(correo);
                        cajaPassword.setText("");
                    }
                    break;
                case RESULT_CANCELED:
                    // Cancelación o cualquier situación de error
                    break;
            }
        }
    }

    //Métodos implementados de la interfaz de DBControl, para saber si el usuario se ha logueado con éxito o no


    public void loginCorrecto() {
        Toast.makeText(getApplicationContext(), "Usuario logueado con éxito", Toast.LENGTH_SHORT).show();

        //Abriremos nueva actividad -- Pasando el correo para saber qué usuario se ha logueado
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);

        Bundle parametros = new Bundle();
        parametros.putString("usuario", nombreUsuario);
        i.putExtras(parametros);

        startActivity(i);

        //Cerramos la actividad para que el usuario, una vez logueado, no pueda volver a la pantalla del log-in
        finish();
    }

    public void loginIncorrecto(){
        Toast.makeText(getApplicationContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendUsername(String username) {
        nombreUsuario = username;
    }

}
