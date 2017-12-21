package com.example.paco.exercici_recyclerview_cardview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    //Constantes
    private static final String LISTA_RESTAURANTES = "param1";

    //Lista con la que trabajará el recyclerView
    private ArrayList<Restaurant> listaRestaurantes;

    private RecyclerView rv;
    private RecyclerView.LayoutManager rvLM;
    private Adaptador adaptador;

    private interfazFragment1 comunicacion;

    public Fragment1() {
        // Required empty public constructor
    }

    //NewInscance -- Pasaremos un arraylist de restaurantes con el que trabajará el recyclerview
    public static Fragment1 newInstance(ArrayList<Restaurant> listaRestaurantes) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LISTA_RESTAURANTES, listaRestaurantes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recuperamos parámetros introducidos al crear el fragment
        if (getArguments() != null) {
            listaRestaurantes = getArguments().getParcelableArrayList(LISTA_RESTAURANTES);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_fragment1, container, false);
    }

    public void onViewCreated(View v, Bundle savedInstanceState){

        //Recuperamos recyclerview del layout
        rv = (RecyclerView)v.findViewById(R.id.recyclerView);


        //Triem el LayoutManager que volem utilitzar i l'assignem a l'objecte recyclerView - FALTA AÑADIR PARÁMETROS
        rvLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(rvLM);

        //Creem l'adaptador que interactuarà amb les dades
        adaptador = new Adaptador(listaRestaurantes);


        //Enllacem el RecyclerView amb l'adaptador
        rv.setAdapter(adaptador);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof interfazFragment1) {
            comunicacion = (interfazFragment1) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        comunicacion = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface interfazFragment1 {
        // TODO: Update argument type and name
        Restaurant obtenerNuevoRestaurante();
    }

    public void anyadirRestaurante(){

        //Añadimos un nuevo restaurante mediante el adaptador
        adaptador.anyadirRestaurante(comunicacion.obtenerNuevoRestaurante());       //Nos comunicamos con MainActivity, obteniendo el nuevo restaurante introducido

        Toast.makeText(getActivity().getApplicationContext(), "Restaurante añadido", Toast.LENGTH_SHORT).show();

        //Hacemos scroll hasta el nuevo restaurante
        rv.scrollToPosition(adaptador.getItemCount()-1);
    }
}
