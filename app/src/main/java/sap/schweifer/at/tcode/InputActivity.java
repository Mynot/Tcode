package sap.schweifer.at.tcode;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import sap.schweifer.at.database.TcDatabase;
import sap.schweifer.at.database.TcTables;

public class InputActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Log.i(this.getLocalClassName(), "Activity gestartet");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TcDatabase db = new TcDatabase(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_input,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tc_save:

                String in_Appl;
                String in_Report;
                String in_Beschreibung;
                String in_Bezeichnug;
                String in_Modul;
                String in_Process;
                long rowID;

                EditText v_Appl = (EditText) findViewById(R.id.input_Appl);
                EditText v_Report = (EditText) findViewById(R.id.input_Code);
                EditText v_Beschreibung = (EditText) findViewById(R.id.input_Bes);
                EditText v_Bezeichung = (EditText) findViewById(R.id.input_Bez);
                EditText v_Modul = (EditText) findViewById(R.id.input_Mod);
                EditText v_Process = (EditText) findViewById(R.id.input_Proz);

                in_Appl =  v_Appl.getText().toString();
                in_Report =  v_Report.getText().toString();
                in_Beschreibung =  v_Beschreibung.getText().toString();
                in_Bezeichnug =  v_Bezeichung.getText().toString();
                in_Modul =  v_Modul.getText().toString();
                in_Process =  v_Process.getText().toString();

                TcDatabase database = new TcDatabase(this);

                rowID = database.insertTc(in_Appl, in_Report, in_Bezeichnug, in_Beschreibung, in_Modul, in_Process);
                Log.i(this.getLocalClassName(), "Datensatz ID: " + rowID + " eingefügt!");
                return true;

            case R.id.action_settings:
                return true;

            case android.R.id.home:
                this.finish();
                Toast.makeText(this, item.getTitle() + "finish", Toast.LENGTH_SHORT)
                        .show();
                Log.i(this.getLocalClassName(), "Activity finish");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
