package com.example.paco.pacomaravillaaliaga_examen_2av.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.paco.pacomaravillaaliaga_examen_2av.MainActivity;


/**
 * Created by Paco on 30/01/2018.
 */

public class MyDialogFragment extends DialogFragment {

    private AlertDialog.Builder adBuilder;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return getAlertDialog();
    }

    public AlertDialog getAlertDialog(){

        adBuilder = new AlertDialog.Builder(getActivity());         //Amb getActivity().getApplicationContext() mostra l'error de AppCompat

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

        return dialog;
    }
}
