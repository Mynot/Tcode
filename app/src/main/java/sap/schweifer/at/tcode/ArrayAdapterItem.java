package sap.schweifer.at.tcode;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Stefan on 12.07.2015.
 */
public class ArrayAdapterItem extends ArrayAdapter<CodeObjects> {


    Context mContext;
    int layoutResourceId;
    CodeObjects applItems[] = null;

    public ArrayAdapterItem(Context mContext, int layoutResourceId, CodeObjects[] data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.applItems = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	/*

    	 */
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

//         object item based on the position
        CodeObjects objectItem = applItems[position];

        // get the TextView and then set the text (item name) and tag (item ID) values
//        TextView textViewAppl = (TextView) convertView.findViewById(R.id.txt_Appl);
//        textViewAppl.setText(objectItem.APPL);
//        textViewAppl.setTag(objectItem.ID);

        TextView textViewCode = (TextView) convertView.findViewById(R.id.txt_Code);
        textViewCode.setText(objectItem.REPORT);
        textViewCode.setTag(objectItem.ID);

        TextView textViewBez = (TextView) convertView.findViewById(R.id.txt_Bezeichnung);
        textViewBez.setText(objectItem.BEZEICHNUNG);
        textViewBez.setTag(objectItem.ID);

        TextView textViewMod = (TextView) convertView.findViewById(R.id.txt_Modul);
        textViewMod.setText(objectItem.MODUL);
        textViewMod.setTag(objectItem.ID);

        TextView textViewPros = (TextView) convertView.findViewById(R.id.txt_Prozess);
        textViewPros.setText(objectItem.PROCESS);
        textViewPros.setTag(objectItem.ID);

        TextView textViewBes = (TextView) convertView.findViewById(R.id.txt_Beschreibung);
        textViewBes.setText(objectItem.BESCHREIBUNG);
        textViewBes.setTag(objectItem.ID);


        return convertView;

    }

}
