package com.example.paco.pacomaravillaaliaga_examen_2av.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Paco on 30/01/2018.
 */

public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    private Calendar c;
    private int anyo, mes, dia;
    private DatePickerDialog dialogoFecha;

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



    //Este método será llamado cuando el usuario elija una fecha
    public void onDateSet(DatePicker view, int anyo, int mes, int dia) {
        //Mostramos toast con la fecha elegida -- Se suma 1 al mes ya que empieza desde 0
        Toast.makeText(getActivity().getApplicationContext(), "Data triada: " + dia + "/" + (mes+1) + "/" + anyo, Toast.LENGTH_SHORT).show();
    }

}