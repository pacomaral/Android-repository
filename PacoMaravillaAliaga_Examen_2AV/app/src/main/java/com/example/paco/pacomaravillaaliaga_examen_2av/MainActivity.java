package com.example.paco.pacomaravillaaliaga_examen_2av;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.paco.pacomaravillaaliaga_examen_2av.Adapter.Adaptador;
import com.example.paco.pacomaravillaaliaga_examen_2av.Fragment.DateDialogFragment;
import com.example.paco.pacomaravillaaliaga_examen_2av.Fragment.MyDialogFragment;
import com.example.paco.pacomaravillaaliaga_examen_2av.Model.Curso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Lista de elementos con la que trabajará el RecyclerView
    private ArrayList<Curso> listaCursos;

    //Objetos necesarios para
    private RecyclerView rv;
    private RecyclerView.LayoutManager rvLM;
    private Adaptador adaptador;

    private AlertDialog.Builder adBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //-----------------------------------------------------------------------

        //Recuperamos recyclerview del layout
        rv = (RecyclerView)findViewById(R.id.recyclerView);


        //Triem el LayoutManager que volem utilitzar i l'assignem a l'objecte recyclerView - FALTA AÑADIR PARÁMETROS
        rvLM = new LinearLayoutManager(this);
        rv.setLayoutManager(rvLM);

        //Crearem la llista de cursos per a pasar-la al adaptador abans de crearlo
        listaCursos = getListaCursos();

        //Creamos el adaptador pasandole la lista
        adaptador = new Adaptador(listaCursos);

        //Enllacem el RecyclerView amb l'adaptador
        rv.setAdapter(adaptador);

        //Inicializamos el builder
        adBuilder = new AlertDialog.Builder(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


    //Método para controlar los eventos de los items del NavigationDrawer
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_data) {
            //Mostramos el datepickerfragment
            DateDialogFragment ddFragment = new DateDialogFragment();
            ddFragment.show(getSupportFragmentManager(), "myDateDialogFragment");
        } else if (id == R.id.nav_info) {
            //Mostramos el diálogfragment
            MyDialogFragment dialogFragment = new MyDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "myDialogFragment");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private ArrayList<Curso> getListaCursos(){

        ArrayList<Curso> lista = new ArrayList<Curso>();

        //Añadimos los cursos que queremos mostrar
        lista.add(new Curso("2n", "DAM", "17-18", 27));
        lista.add(new Curso("1r", "DAM", "17-18", 30));
        lista.add(new Curso("2n", "DAW", "17-18", 20));
        lista.add(new Curso("1r", "DAW", "17-18", 30));
        lista.add(new Curso("2n", "DAM", "16-17", 25));
        lista.add(new Curso("1r", "DAM", "16-17", 32));
        lista.add(new Curso("2n", "DAW", "16-17", 23));
        lista.add(new Curso("1r", "DAW", "16-17", 29));




        //Devolvemos la lista
        return lista;

    }

    private void mostrarDialogoInfo(){

        StringBuilder myMsg = new StringBuilder();

        myMsg.append("Examen PMDM");
        myMsg.append("\n");
        myMsg.append("Paco Maravilla Aliaga");
        myMsg.append("\n");
        myMsg.append("frmaal01@gmail.com");

        adBuilder.setMessage(myMsg.toString()).setTitle("Informació");

        adBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //No haremos nada, solo se cerrará el diálogo
            }
        });

        AlertDialog dialog = adBuilder.create();
        dialog.show();
    }
}
