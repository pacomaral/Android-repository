package com.example.paco.exercici_alertdialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class HoraDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    private Date d;
    private Calendar c;
    private int hora, minuto;
    private TimePickerDialog dialogoHora;

    private DialogoHoraInterfaz comunicacion;

    public Dialog onCreateDialog(Bundle b){

        //Se utilizará por defecto la fecha actual
        c = Calendar.getInstance();

        d = c.getTime();

        //Estos métodos están deprecated desde API 1 -- Los utilizaremos para utilizar la hora actual por defecto
        hora = d.getHours();
        minuto = d.getMinutes();

        //Creamos el dialogo y lo devolvemos
        dialogoHora = new TimePickerDialog(getActivity(), this, hora, minuto, true);            //True para formato de 24h
        return dialogoHora;
    }


    //Lo implementaremos para obtener el objeto de la interfaz para podernos comunicar
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            comunicacion = (DialogoHoraInterfaz) context;
        } catch (ClassCastException castException) {

        }
    }

    //Este método será llamado cuando el usuario elija una hora
    public void onTimeSet(TimePicker view, int hora, int minuto) {
        //Actualizamos datos de MainActivity
        comunicacion.actualizarHora(hora, minuto);
    }

    //Interfaz para comunicarnos con el MainActity cuando el usuario elija la hora
    public interface DialogoHoraInterfaz{
        public void actualizarHora(int hora, int minuto);
    }
}
