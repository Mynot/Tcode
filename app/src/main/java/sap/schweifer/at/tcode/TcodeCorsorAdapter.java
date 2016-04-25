package sap.schweifer.at.tcode;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

import sap.schweifer.at.database.TcTables;

/**
 * Created by Stefan on 02.04.2016.
 */
public class TcodeCorsorAdapter extends CursorAdapter {
    private static final String TAG = TcodeCorsorAdapter.class.getSimpleName();


    private LayoutInflater layoutInflater = null;


    public TcodeCorsorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

//        layoutInflater= LayoutInflater.from(context);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        return layoutInflater.inflate(R.layout.rel_datenbankeintrag, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

//      Text Views inizialisieren und deklarieren
        TextView etxtleadingLetterReport = null;
        TextView etxtReport = null;
        TextView etxtBezeichnung = null;
        TextView etxtBeschreibung = null;
        TextView etxtModul = null;
        TextView etxtProcess = null;
        Drawable vimgLeadingLetter = null;

//      Image View deklarieren udn initialisieren
//        ImageView imgReportledingLetter = null;


        etxtleadingLetterReport = (TextView) view.findViewById(R.id.txt_leading_Letter_Report);
        etxtReport = (TextView) view.findViewById(R.id.txt_Report);
        etxtBezeichnung = (TextView) view.findViewById(R.id.txt_Bezeichnung);
        etxtBeschreibung = (TextView) view.findViewById(R.id.txt_Beschreibung);
        etxtModul = (TextView) view.findViewById(R.id.txt_Modul);
        etxtProcess = (TextView) view.findViewById(R.id.txt_Prozess);
//        imgReportledingLetter = (ImageView) view.findViewById(R.id.imgleadingLetterReport);
        vimgLeadingLetter = (Drawable) view.getResources().getDrawable(R.drawable.sh_main_list_button_shape, context.getTheme());


//        Log.d(TAG, "bindView Cursor: " + cursor);

//      Werte aus Datenbank auslesen
        String ID = cursor.getString(cursor.getColumnIndex(TcTables.ID));
        String txtReport = cursor.getString(cursor.getColumnIndex(TcTables.TX_REPORT));
        String txtBezeichnung = cursor.getString(cursor.getColumnIndex(TcTables.TX_BEZ));
        String txtBeschreibung = cursor.getString(cursor.getColumnIndex(TcTables.TX_BES));
        String txtModul = cursor.getString(cursor.getColumnIndex(TcTables.TX_MOD));
        String txtProcess = cursor.getString(cursor.getColumnIndex(TcTables.TX_PROC));

//        Farb integer zusammensetzen
        String color = txtReport.substring(0, 1);
//      "R.color." +
//        Log.d(TAG, "biParse INT Color: " + "R.color." + txtReport.substring(0, 1));

//        Farb Code ermittln
        int colorCode = getResId(color, R.color.class);
//        Prüfen ob Farbcode vorhanden sonst default belassen
        if (colorCode != -1) {
            int rcolor = ContextCompat.getColor(context, colorCode);
//            Log.d(TAG, "color Compat rcolor: " + rcolor + " - " + colorCode);


            if (vimgLeadingLetter != null) {
                vimgLeadingLetter.setColorFilter(rcolor, PorterDuff.Mode.SRC_ATOP);
//                Log.d(TAG, "set Color: " + rcolor);
            }
        }
        etxtleadingLetterReport.setBackground(vimgLeadingLetter);


//      Werte den Textfeldern zuweisen
        etxtleadingLetterReport.setText(txtReport.substring(0, 1));
        etxtReport.setText(txtReport);
        etxtBezeichnung.setText(txtBezeichnung);
        etxtBeschreibung.setText(txtBeschreibung);
        etxtModul.setText(txtModul);
        etxtProcess.setText(txtProcess);


//      Tag setzen
        etxtleadingLetterReport.setTag(ID);
        etxtReport.setTag(ID);
        etxtBezeichnung.setTag(ID);
        etxtBeschreibung.setTag(ID);
        etxtModul.setTag(ID);
        etxtProcess.setTag(ID);
//        imgReportledingLetter.setTag(ID);


    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getField(resName);
//            Log.d(TAG, "getResId: idField " + idField);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getResId: " + e);
            return -1;
        }
    }
}
