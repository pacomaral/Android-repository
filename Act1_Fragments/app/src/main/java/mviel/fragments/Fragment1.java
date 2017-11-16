package mviel.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment1 extends Fragment {
    private RelativeLayout layoutF1;
    private FragmentManager fm;
    private FragmentTransaction ft;

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        //Recuperamos el layout del fragment1 del recurso xml
        layoutF1 = (RelativeLayout) v.findViewById(R.id.layoutF1);

        //Listener onClick
        layoutF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                //Si no está el fragment2 mostrado, lo mostramos
                if(!((MainActivity)getActivity()).estaFragment2EnActivity()) {

                    Toast.makeText(getActivity(), "Mostrant Fragment 2", Toast.LENGTH_SHORT).show();
                    ft.add(R.id.espaiFragment2, Fragment2.newInstance("", ""));

                    //Añadimos addtobackstack para que, al presionar atrás, quite la última transacción añadida
                    ft.addToBackStack("");

                    ft.commit();
                }
                //Si también está el 3, no podremos ocultarlos
                if(((MainActivity)getActivity()).estaFragment3EnActivity()){
                    Toast.makeText(getActivity(), "No pots ocultar el Fragment 2 ja que hauras d'ocultar primer el 3", Toast.LENGTH_SHORT).show();
                }
                //Si está el fragment 2 y no el 3, ocultamos el 2
                else if(((MainActivity)getActivity()).estaFragment2EnActivity()) {
                    Toast.makeText(getActivity(), "Eliminant Fragment 2 amb un click", Toast.LENGTH_SHORT).show();
                    ft.remove(getActivity().getFragmentManager().findFragmentById(R.id.espaiFragment2));

                    //Añadimos esto para poder eliminarlo de la transacción también, ya que lo habíamos añadido con addToBackStack()
                    fm.popBackStack();
                }

                /*
                 *    Con ((MainActivity)getActivity()) se hace referencia a la actividad en la cuál está este Fragment
                 *    estático
                 */
            }
        });


        //Añadimos un onLongClickListener
        layoutF1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                //Si está el fragment 3 mostrado, ocultamos fragment 3 y 2
                if(((MainActivity)getActivity()).estaFragment3EnActivity()){
                    //Mostramos un toast con info
                    Toast.makeText(getActivity(), "Eliminant Fragments 2 i 3 amb click llarg", Toast.LENGTH_SHORT).show();
                    //Los eliminamos
                    ft.remove(getActivity().getFragmentManager().findFragmentById(R.id.espaiFragment2));
                    ft.remove(getActivity().getFragmentManager().findFragmentById(R.id.espaiFragment3));

                    //Hacemos 2 popBackStack
                    fm.popBackStack();
                    fm.popBackStack();
                }
                //Si no esta el 3 y sí está el 2, quitamos el 2
                else if(!((MainActivity)getActivity()).estaFragment3EnActivity() && ((MainActivity)getActivity()).estaFragment2EnActivity()){
                    //Mostramos info
                    Toast.makeText(getActivity(), "Eliminant Fragment 2 amb click llarg", Toast.LENGTH_SHORT).show();
                    //Eliminamos el 2
                    ft.remove(getActivity().getFragmentManager().findFragmentById(R.id.espaiFragment2));

                    fm.popBackStack();
                }
                //Si no están ni el 2 ni el 3, decimos al usuario que no se puede eliminar ninguno
                else{
                    Toast.makeText(getActivity(), "No es pot eliminar ningún Fragment", Toast.LENGTH_SHORT).show();
                }

                //Devolvemos true para que no haga caso de los otros listeners como el onClick, si no seguiría al otro

                /**
                 * onLongClick() - This returns a boolean to indicate whether you have consumed the event and it should not be
                 * carried further. That is, return true to indicate that you have handled the event and it should stop here;
                 * return false if you have not handled it and/or the event should continue to any other on-click listeners.
                 */

                return true;
            }
        });

        return v;
    }
}
