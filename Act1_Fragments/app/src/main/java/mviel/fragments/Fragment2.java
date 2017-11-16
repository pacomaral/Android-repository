package mviel.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Fragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentManager fm;
    private FragmentTransaction ft;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Lo utilizaremos para poder llamar a los métodos de las interfaces sobreeescribidos en el activity
    private interfazFragment2 comunicacion;
    //private Fragment3.interfazFragment3 comunicacionFragment3;
    //private Fragment3.interfazFragment3 comunicacionFragment3;

    //Para contar los clicks que llevamos
    private int contador;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment2() {
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
        View v= inflater.inflate(R.layout.fragment_fragment2, container, false);

        FrameLayout fl = (FrameLayout) v.findViewById(R.id.FrameLayout1);

        //Inicializamos el contador a 0
        contador = 0;

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cada vez que hagamos click en el fragmento 2, sumaremos un valor al contador y lo mostramos en el fragmento 3
                contador++;


                //comunicacion.enviarTextoFragmento3(String.valueOf(contador));

                //Transacción
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                //Si no está el fragment 3 en la activity, lo mostramos
                if (!comunicacion.estaFragment3EnActivity()) {

                    Toast.makeText(getActivity(), "Mostrant Fragment3", Toast.LENGTH_SHORT).show();
                    ft.add(R.id.espaiFragment3, Fragment3.newInstance("", ""));
                    //Añadimos addtobackstack para que, al presionar atrás, quite la última transacción añadida

                    ft.addToBackStack("");
                    //Enviamos el valor del contador y cambiamos el valor de su textview


                }
                //Si ya estaba en el activity, ocultamos el ragmento 3
                else{

                    //comunicacion.pasarValorContador();

                    Toast.makeText(getActivity(), "Amagant Fragment3", Toast.LENGTH_SHORT).show();


                    //ft.remove(getActivity().getFragmentManager().findFragmentById(R.id.espaiFragment3));

                    //Añadimos esto para poder eliminarlo de la transacción también, ya que lo habíamos añadido con addToBackStack()
                    fm.popBackStack();
                }

                ft.commit();

            }
    });
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //Recogemos en el onAttach dos objetos con los que nos comunicaremos
            comunicacion = (interfazFragment2) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement interfazFragment2");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface interfazFragment2{
        boolean estaFragment3EnActivity();
        int pasarValorContador();
    }

    public int getContador(){
        return this.contador;
    }

}
