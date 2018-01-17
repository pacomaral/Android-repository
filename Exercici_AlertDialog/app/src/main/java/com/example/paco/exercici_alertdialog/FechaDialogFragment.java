package com.example.paco.exercici_alertdialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;


public class FechaDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    private Calendar c;
    private int anyo, mes, dia;
    private DatePickerDialog dialogoFecha;

    private DialogoFechaInterfaz comunicacion;

    //Se llamará cuando instanciemos el fragment
    public Dialog onCreateDialog(Bundle b){

        //Se utilizará por defecto la fecha actual
        c = Calendar.getInstance();
        anyo = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);

        //Creamos el dialogo y lo devolvemos
        dialogoFecha = new DatePickerDialog(getActivity(), this, anyo, mes, dia);
        return dialogoFecha;
    }


    //Lo implementaremos para obtener el objeto de la interfaz para podernos comunicar
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            comunicacion = (DialogoFechaInterfaz) context;
        } catch (ClassCastException castException) {

        }
    }

    //Este método será llamado cuando el usuario elija una fecha
    public void onDateSet(DatePicker view, int anyo, int mes, int dia) {
        //Actualizaremos los datos
        comunicacion.actualizarFecha(anyo, mes+1, dia);         //Sumamos 1 al mes ya que se empieza desde 0
    }

    //Interfaz para comunicarnos con el MainActity cuando el usuario elija la fecha
    public interface DialogoFechaInterfaz{
        public void actualizarFecha(int anyo, int mes, int dia);
    }
}
