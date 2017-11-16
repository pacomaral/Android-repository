package mviel.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Fragment2.interfazFragment2, Fragment3.interfazFragment3{


    private int contador=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Método para saber si el fragmento 3 está en esta actividad (se sobreescribe a la interfaz onFragmentInteractionListener2
    public boolean estaFragment3EnActivity() {
       Fragment3 f3= (Fragment3)getFragmentManager().findFragmentById(R.id.espaiFragment3);
        if(f3==null)
            return false;
        else
            return true;
    }

    //Método para saber si el fragmento 2 está en esta actividad
    public boolean estaFragment2EnActivity(){
        Fragment2 f2 = (Fragment2)getFragmentManager().findFragmentById(R.id.espaiFragment2);
        if(f2==null){
            return false;
        }
        else{
            return true;
        }
    }

    //Método que tienen las 2 interfaces implementadas



    //Método sobreescribido de la interfaz del fragment 3
    public int recibirContador(){

        //Recibimos el valor del contador del fragment 2
        int contador = pasarValorContador();

        Toast.makeText(getApplicationContext(), "Contador pasado al fragment 3", Toast.LENGTH_SHORT).show();

        return contador;
    }

    //Método sobreeescribido de la interfaz del fragment 2
    public int pasarValorContador(){
        Fragment2 f2 = (Fragment2)getFragmentManager().findFragmentById(R.id.espaiFragment2);

        int num = f2.getContador();

        return num;
    }




}
