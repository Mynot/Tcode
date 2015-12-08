package sap.schweifer.at.tcode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Stefan on 30.11.2015.
 */
public class CodeAdapter extends ArrayAdapter<CodeObjects> {

    Context mContext;
    int layoutResourceId;
    CodeObjects data[] = null;

    public CodeAdapter(Context mContext, int layoutResourceId, CodeObjects[] data){

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
