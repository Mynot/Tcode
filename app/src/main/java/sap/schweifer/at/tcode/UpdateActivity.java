package sap.schweifer.at.tcode;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sap.schweifer.at.database.TcDatabase;
import sap.schweifer.at.database.TcTables;

public class UpdateActivity extends AppCompatActivity {
    static final String TAG = UpdateActivity.class.getSimpleName();

    //private CursorAdapter ca;

    private String in_Appl;
    private int in_ID;

    //Instanz der Main Activity erstellen
    MainActivity getAppl;

    //Views instanziieren
    TextView v_Appl = null;

    EditText v_Report = null;
    EditText v_Beschreibung = null;
    EditText v_Bezeichung = null;
    EditText v_Modul = null;
    EditText v_Process = null;

    // Datenbank instanziieren
    TcDatabase database = null;


    String txt_Report = "";
    String txt_Beschreibung = "";
    String txt_Bezeichung = "";
    String txt_Modul = "";
    String txt_Prozess = "";


    // TODO: 24.01.2016 Resourcen Freigabe prüfen! App belegt Speicher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_delete_data);
        Log.i(this.getLocalClassName(), "Activity gestartet");

        //Extras aus Bundle abfragen
        Bundle bundle = getIntent().getExtras();

        in_ID = bundle.getInt("cID");
        Log.d(TAG, "onCreate ID aus Extras: " + in_ID);

        //Views zuweisen
        this.v_Report = (EditText) findViewById(R.id.input_Code);
        this.v_Beschreibung = (EditText) findViewById(R.id.input_Bes);
        this.v_Bezeichung = (EditText) findViewById(R.id.input_Bez);
        this.v_Modul = (EditText) findViewById(R.id.input_Mod);
        this.v_Process = (EditText) findViewById(R.id.input_Proz);


        //Actionbar aktivieren
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        getAppl = new MainActivity();
        //Anwendung aus Main abfragen und zuweisen
        in_Appl = getAppl.getSelApplication();

        //Anwendung in Text View schreiben
        v_Appl = (TextView) findViewById(R.id.input_Appl);
        v_Appl.setText(in_Appl);


        database = new TcDatabase(getApplicationContext());
        Cursor c = database.queryDatasetAppl(String.valueOf(in_ID));
        c.moveToFirst();

//      Werte aus Datenbank abrufen
        txt_Report = c.getString(c.getColumnIndex(TcTables.TX_REPORT));
        txt_Beschreibung = c.getString(c.getColumnIndex(TcTables.TX_BES));
        txt_Bezeichung = c.getString(c.getColumnIndex(TcTables.TX_BEZ));
        txt_Modul = c.getString(c.getColumnIndex(TcTables.TX_MOD));
        txt_Prozess = c.getString(c.getColumnIndex(TcTables.TX_PROC));

        Log.d(TAG, "onCreate: " + txt_Report);

//      Werte in  Textview schreiben
        v_Report.setText(txt_Report);
        v_Beschreibung.setText(txt_Beschreibung);
        v_Bezeichung.setText(txt_Bezeichung);
        v_Modul.setText(txt_Modul);
        v_Process.setText(txt_Prozess);

        c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_update_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//          Case Update: geänderte Daten werden in die Datenbank übernommen
            case R.id.tc_update:


                String in_Report;
                String in_Beschreibung;
                String in_Bezeichnung;
                String in_Modul;
                String in_Process;
                long rowID;


                in_Report = v_Report.getText().toString().toUpperCase();
                in_Beschreibung = v_Beschreibung.getText().toString();
                in_Bezeichnung = v_Bezeichung.getText().toString();
                in_Modul = v_Modul.getText().toString().toUpperCase();
                in_Process = v_Process.getText().toString();


                rowID = database.updateTc(in_ID, in_Report, in_Bezeichnung, in_Beschreibung, in_Modul, in_Process);
                Log.i(this.getLocalClassName(), "Datensatz ID: " + rowID + " aktualisierent!");

                database.close();

                this.finish();

                return true;
//          Case delete: Datensatz wird gelöscht
            case R.id.tc_delete:

                rowID = database.deleteDataset(String.valueOf(in_ID));
                Log.i(TAG, "Datensatz ID: " + rowID + " gelöscht!");

                database.close();

                this.finish();

            case R.id.action_settings:
                return true;

//          Case home: Activity wird beendet
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
