package sap.schweifer.at.tcode.Listener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import sap.schweifer.at.tcode.ActiveApplication;
import sap.schweifer.at.tcode.R;
import sap.schweifer.at.tcode.UpdateActivity;

/**
 * Created by Stefan on 09.03.2016.
 * On Button Click listener für das einfügen eines Moduls oder Prozedur aus der Activity UdateActivity
 */
public class OnUpdateButtonClicklisterner implements View.OnClickListener {


    AlertDialog builder;
    private static UpdateActivity updateActivity = null;


    /*
        Standard Konstruktor
        */
    public OnUpdateButtonClicklisterner() {

    }


    @Override
    public void onClick(View v) {
        updateActivity = (UpdateActivity) v.getContext();

        LayoutInflater insertAlertInlator = updateActivity.getLayoutInflater();

        switch (v.getId()) {

            case R.id.but_mod_add:

                //      TextView für Alertdialog instanziieren und aufblasen
                final View layoutMod = insertAlertInlator.inflate(R.layout.box_insert_item, null);


                AlertDialog.Builder msgInsertMod = new AlertDialog.Builder(v.getContext());
                msgInsertMod.setTitle(R.string.txt_Title_insert_item)
                        .setView(layoutMod)
                        .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//
                                builder.cancel();


                            }
                        })
                        .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                EditText insertItem = (EditText) layoutMod.findViewById(R.id.insertItemSpinner);

                                String item = insertItem.getText().toString();

                                long dataset = updateActivity.getDatabase().insertModul(item.toUpperCase(), ActiveApplication.getAnwendung());


                                updateActivity.createModulSpinner();

                                builder.dismiss();


                            }
                        });

                builder = msgInsertMod.show();

                break;


            case R.id.but_proz_add:

                //      TextView für Alertdialog instanziieren und aufblasen
                final View layoutProz = insertAlertInlator.inflate(R.layout.box_insert_item, null);

                AlertDialog.Builder msgInsertProz = new AlertDialog.Builder(v.getContext());
                msgInsertProz.setTitle(R.string.txt_Title_insert_item)
                        .setView(layoutProz)
                        .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                builder.cancel();


                            }
                        })
                        .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText insertItem = (EditText) layoutProz.findViewById(R.id.insertItemSpinner);

                                String item = insertItem.getText().toString();

                                long dataset = updateActivity.getDatabase().insertProcess(item, ActiveApplication.getAnwendung());


                                updateActivity.createProcessSpinner();

                                builder.dismiss();


                            }
                        });

                builder = msgInsertProz.show();


        }
    }
}
