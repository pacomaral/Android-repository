package com.example.paco.examen_ej2_di;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listaElementos;
    private String[] elementos;
    private String nombreElemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaElementos = (ListView)findViewById(R.id.listaElementos);

        //Rellenamos los elementos que queramos en el array
        elementos = new String[]{"Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve",
                "Diez", "Once", "Doce", "Trece", "Catorce", "Quince", "Dieciseis", "Diecisiete",
                "Diecinueve", "Veinte"};

        //Creamos arrayAdapter
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, elementos);

        //Asignamos el adaptador al listview
        listaElementos.setAdapter(adaptador);

        //Nos falta configurar el contextMenu en todos los elementos de in ListView
        registerForContextMenu(listaElementos);
    }


    //Método que configurará e inflará el contextmenu que tenemos creado en el recurso menú
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        //Con esto obtendremos sobre que elemento del textview se ha hecho click
        AdapterView.AdapterContextMenuInfo información = (AdapterView.AdapterContextMenuInfo) menuInfo;
        nombreElemento = ((TextView) información.targetView).getText().toString();
        //Lo ponemos en el menú
        menu.setHeaderTitle("Número " + nombreElemento);

        //Inflamos el menú
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.opcionCompartir:
                Toast.makeText(getApplicationContext(), "Se ha compartido el número " + nombreElemento, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opcionEditar:
                Toast.makeText(getApplicationContext(), "Se ha editado el número " + nombreElemento, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opcionEliminar:
                Toast.makeText(getApplicationContext(), "Se ha borrado el número " + nombreElemento, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
