package com.example.paco.pacomaravillaaliagaexamen;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class DadesPersonalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private interfazFragmentDades comunicacion;

    private Button botonEnviarDatos;
    private EditText cajaNombre, cajaEdad;
    private RadioButton opcionHombre, opcionMujer;

    public DadesPersonalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DadesPersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DadesPersonalFragment newInstance(String param1, String param2) {
        DadesPersonalFragment fragment = new DadesPersonalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dades_personal, container, false);
    }

    /*
    *   Este método es llamado cuando justo después del onCreateView (cuando se infla el layout del fragment), si este ha ido
    *   correctamente.
    *
    *   Es una buena práctica recoger aquí los elementos del layout para poder trabajar con ellos. En este caso recuperamos
    *   el texto del contador para posteriormente, recibir el contador gracias al método sobreescrito de la interfaz en el
    *   MainActivity, y después asignarlo al textoContador.
    *
    *   Esto se hará cada vez que se muestre el layout, por lo que obtenemos la funcionalidad que queríamos en el apartado D
    *   de la actividad.
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //Recogemos elemento del layout
        botonEnviarDatos = (Button)view.findViewById(R.id.botonEnviarDatos);
        cajaEdad = (EditText) view.findViewById(R.id.cajaEdad);
        cajaNombre = (EditText) view.findViewById(R.id.cajaNombre);
        opcionHombre = (RadioButton) view.findViewById(R.id.opcionHombre);
        opcionMujer = (RadioButton) view.findViewById(R.id.opcionMujer);

        //Configuramos listener
        botonEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprobamos que se hayan introducido datos
                if(String.valueOf(cajaEdad.getText()).isEmpty() || String.valueOf(cajaNombre.getText()).isEmpty() || (!opcionMujer.isChecked() && !opcionHombre.isChecked())){
                    Toast.makeText(getActivity(), "Et falta algo per introduir", Toast.LENGTH_SHORT).show();
                }
                //Si esta tot, ho guardem al activity principal
                else{
                    String nombre = String.valueOf(cajaNombre.getText());
                    String edad = String.valueOf(cajaEdad.getText());
                    String sexo = null;
                    if(opcionHombre.isChecked()){
                        sexo = "Mascle";
                    }
                    else{
                        sexo = "Femella";
                    }
                    comunicacion.enviarDatos(nombre, edad, sexo);
                    Toast.makeText(getActivity(), "Dades guardades correctament", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //Recuperamos el objeto de la interfaz haciendo referencia a la activity de parámetro del onAttach
            //Esta activity será la que contiene el fragmento, y se puede castear a la interfaz porque
            //la implementa
            comunicacion = (interfazFragmentDades) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement comunicacionFragment3");
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
    public interface interfazFragmentDades {
        // TODO: Update argument type and name
        void enviarDatos(String nombre, String edad, String sexo);
    }
}
