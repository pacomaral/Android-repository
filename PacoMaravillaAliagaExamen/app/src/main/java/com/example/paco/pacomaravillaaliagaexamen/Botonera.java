package com.example.paco.pacomaravillaaliagaexamen;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;


public class Botonera extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Objetos y variables necesarias
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Button botonDatos, botonAficiones;
    private ImageButton botonInfo;
    private boolean estaFragmentDades = false;
    private boolean estaFragmentAficions = false;

    private static final int PETICION = 1;

    public Botonera() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_botonera, container, false);

        //Recuperamos los elementos del layout
        botonDatos = (Button)v.findViewById(R.id.botonDatos);
        botonAficiones = (Button)v.findViewById(R.id.botonAficiones);
        botonInfo = (ImageButton)v.findViewById(R.id.botonInfo);

        //Añadimos listener
        botonDatos.setOnClickListener(this);
        botonAficiones.setOnClickListener(this);
        botonInfo.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {


        //Si es el botón de datos, cargamos fragment
        if(v.getId() == R.id.botonDatos){

            if(!estaFragmentDades) {
                fm = getFragmentManager();
                ft = fm.beginTransaction();

                //Si está el fragmento de aficiones, lo quitamos
                if (estaFragmentAficions) {
                    ft.remove(getActivity().getFragmentManager().findFragmentById(R.id.espaiFragment));

                    //Quitamos de la pila de transacciones
                    fm.popBackStack();

                    estaFragmentAficions = false;
                }


                //Añadimos el fragmento 2 dinamicamente, al hacer click en el fragmento 1
                ft.add(R.id.espaiFragment, DadesPersonalFragment.newInstance("", ""));

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                ft.addToBackStack(null);

                //Lo mostramos
                ft.commit();


                //Cambiamos semáforo
                estaFragmentDades = true;
            }

        }
        else if(v.getId() == R.id.botonAficiones){

            if(!estaFragmentAficions) {
                fm = getFragmentManager();
                ft = fm.beginTransaction();

                //Si está el otro fragmento, lo tendremos que eliminar antes de mostrar el otro
                if (estaFragmentDades) {
                    ft.remove(getActivity().getFragmentManager().findFragmentById(R.id.espaiFragment));

                    fm.popBackStack();

                    estaFragmentDades = false;
                }

                //Añadimos el fragmento 2 dinamicamente, al hacer click en el fragmento 1
                ft.add(R.id.espaiFragment, AficionsFragment.newInstance("", ""));

                //Para que al darle al botón de atrás deshaga la transacción
                ft.addToBackStack(null);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                //Lo mostramos
                ft.commit();

                estaFragmentAficions = true;
            }
        }

        else if(v.getId() == R.id.botonInfo){

            //Lanzamos la subactivity mediante el método implementado en el mainactivity
            ((MainActivity)getActivity()).lanzarSubActivity();
        }
    }

}
