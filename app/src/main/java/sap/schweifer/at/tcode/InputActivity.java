package sap.schweifer.at.tcode;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sap.schweifer.at.database.TcDatabase;

public class InputActivity extends AppCompatActivity {
    static final String TAG = InputActivity.class.getSimpleName();

    //  Datenbank initialisieren
    TcDatabase database;
    //  Main Activity instanziieren
    MainActivity getAppl = null;

    private String in_Appl = null;

    //Textview für Anwendung
    TextView v_Appl;

    //  Eingabefelder initialisieren
    EditText v_Report = null;
    EditText v_Beschreibung = null;
    EditText v_Bezeichung = null;
    EditText v_Modul = null;
    EditText v_Process = null;


    // TODO: 24.01.2016 Resourcen Freigabe prüfen! App belegt Speicher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Log.i(this.getLocalClassName(), "Activity gestartet");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        database = new TcDatabase(this);

//      Anwendung ermitteln und Variable Wert zuweisen
        getAppl = new MainActivity();
        in_Appl = getAppl.getSelApplication();
        Log.d(TAG, "onOptionsItemSelected: " + in_Appl);
//      Textview Anwenungs zuweisen
        v_Appl = (TextView) findViewById(R.id.input_Appl);
        Log.d(TAG, "onOptionsItemSelected: " + v_Appl);
        v_Appl.setText(in_Appl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_input, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tc_save:


                String in_Report;
                String in_Beschreibung;
                String in_Bezeichnug;
                String in_Modul;
                String in_Process;
                long rowID;

                v_Report = (EditText) findViewById(R.id.input_Code);
                v_Beschreibung = (EditText) findViewById(R.id.input_Bes);
                v_Bezeichung = (EditText) findViewById(R.id.input_Bez);
                v_Modul = (EditText) findViewById(R.id.input_Mod);
                v_Process = (EditText) findViewById(R.id.input_Proz);


                in_Report = v_Report.getText().toString().toUpperCase();
                in_Beschreibung = v_Beschreibung.getText().toString();
                in_Bezeichnug = v_Bezeichung.getText().toString();
                in_Modul = v_Modul.getText().toString().toUpperCase();
                in_Process = v_Process.getText().toString();


                rowID = database.insertTc(in_Appl, in_Report, in_Bezeichnug, in_Beschreibung, in_Modul, in_Process);
                Log.i(this.getLocalClassName(), "Datensatz ID: " + rowID + " eingefügt!");

                database.close();
                this.finish();

                return true;


            case R.id.action_settings:
                return true;

            case android.R.id.home:
                database.close();
                this.finish();
                Log.i(this.getLocalClassName(), "Activity finish");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
