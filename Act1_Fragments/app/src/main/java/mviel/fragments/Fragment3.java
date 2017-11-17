package mviel.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Fragment3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Objeto de la interfaz para poder comunicarnos
    private interfazFragment3 comunicacion;


    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment3() {
        // Required empty public constructor
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
        View v= inflater.inflate(R.layout.fragment_fragment3, container, false);

        return v;
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

        TextView textoContador = (TextView) view.findViewById(R.id.textoContador);

        //Cuando se crea en la actividad, recuperamos al textview y le ponemos el contador que obtenemos
        int num = comunicacion.recibirContador();

        textoContador.setText(String.valueOf(num));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //Recuperamos el objeto de la interfaz haciendo referencia a la activity de parámetro del onAttach
            //Esta activity será la que contiene el fragmento, y se puede castear a la interfaz porque
            //la implementa
            comunicacion = (interfazFragment3) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement comunicacionFragment3");
        }



    }

    @Override
    public void onDetach() {
        super.onDetach();
        //En el onDetach, ponemos a null la comunicación.
        comunicacion = null;
    }


    //Interfaz para poder comunicarnos con el MainActivity
    public interface interfazFragment3{
        //Este método lo sobreeescribiremos en MainActivity y desde él obtendremos el valor del contador del fragmento 2.
        int recibirContador();
    }



}
