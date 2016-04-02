package sap.schweifer.at.tcode.Listener;

import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;

import sap.schweifer.at.database.TcTables;
import sap.schweifer.at.tcode.ActiveApplication;

/**
 * Created by Stefan on 14.02.2016.
 * Listener der Spinner Items Process in Input und Update Activity.
 */
public class OnModuleItemInputListener
        implements AdapterView.OnItemSelectedListener {
    private static final String TAG = OnModuleItemInputListener.class.getSimpleName();


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Cursor l_cursor = null;

        l_cursor = (Cursor) parent.getSelectedItem();

        l_cursor.moveToPosition(position);


        ActiveApplication.setModul(l_cursor.getString(l_cursor.getColumnIndex(TcTables.TX_MOD)));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

