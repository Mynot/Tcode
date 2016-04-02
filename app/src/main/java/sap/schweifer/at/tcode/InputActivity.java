package sap.schweifer.at.tcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import sap.schweifer.at.database.TcDatabase;
import sap.schweifer.at.database.TcTables;
import sap.schweifer.at.tcode.Listener.OnModuleItemInputListener;
import sap.schweifer.at.tcode.Listener.OnProcedureItemInputListener;

public class InputActivity extends AppCompatActivity {
    static final String TAG = InputActivity.class.getSimpleName();

    //  Datenbank initialisieren
    TcDatabase database;


    // String Variablen
    private String in_Appl = null;


    //  Eingabefelder initialisieren
    EditText v_Report = null;
    EditText v_Beschreibung = null;
    EditText v_Bezeichung = null;
    Spinner v_Modul = null;
    Spinner v_Process = null;

    //  Image Button instanziieren und initialisieren
    ImageButton but_modItem_add = null;
    ImageButton but_prozItem_add = null;

    public SimpleCursorAdapter spinAdapterProcedure = null;// TODO: 15.03.2016 Spinner Aktualisierung mit change Cursor einbauen 
    public SimpleCursorAdapter spinAdapterModul = null;


    private LayoutInflater insertAlertInlator;

    //Cursor für das auslesen der Proceduren initialisieren
    public Cursor procCursor = null;
    public Cursor modulCursor = null;

    private AlertDialog builder;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public TcDatabase getDatabase() {
        return database;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(ActiveApplication.getAnwendung());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Log.i(this.getLocalClassName(), "Activity gestartet");


//      Actionbar Initialisieren und Instanziieren
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


//      Datenbank zuweisen
        database = new TcDatabase(this);

//      Anwendung ermitteln und Variable Wert zuweisen

        in_Appl = ActiveApplication.getAnwendung();
        Log.d(TAG, "aktuelle Anwendung: " + in_Appl);

        // Button zuweisen
        this.but_modItem_add = (ImageButton) findViewById(R.id.but_mod_add);
        this.but_prozItem_add = (ImageButton) findViewById(R.id.but_proz_add);

//        Button OnClick Listener Instanziieren
//        OnInputButtonClicklistener onInsertItemClicklistener = new OnInputButtonClicklistener();

//        OnClicklistener zuweisen


/**
 *    OnClickListener zum einfügen eines Modul Items
 **/
        but_modItem_add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                insertAlertInlator = getLayoutInflater();
                final View layoutMod = insertAlertInlator.inflate(R.layout.box_insert_item, null);


                AlertDialog.Builder msgInsertMod = new AlertDialog.Builder(v.getContext());
                msgInsertMod.setTitle(R.string.txt_Title_insert_item)
                        .setView(layoutMod)
                        .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (builder != null) {
                                    builder.cancel();
                                }

                            }
                        })
                        .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                EditText insertItem = (EditText) layoutMod.findViewById(R.id.insertItemSpinner);

                                String item = insertItem.getText().toString();

                                long dataset = getDatabase().insertModul(item.toUpperCase(), ActiveApplication.getAnwendung());

                                if (dataset != -1) {
                                    Toast.makeText(InputActivity.this, getString(R.string.txt_Dataset_inserted), Toast.LENGTH_LONG).show();
                                }

//                                inputActivity.createModulSpinner();
//                                if (spinAdapterModul == null) {
//                                    createModulSpinner();
//                                } else {
//
//                                }
//                                spinAdapterModul.changeCursor(modulCursor);
                                createModulSpinner();
                                builder.dismiss();


                            }
                        });

                builder = msgInsertMod.show();
            }
        });

        /**
         *    OnClickListener zum einfügen eines Modul Items
         **/

        but_prozItem_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              TextView für Alertdialog instanziieren und aufblasen


                insertAlertInlator = getLayoutInflater();

                final View layoutProz = insertAlertInlator.inflate(R.layout.box_insert_item, null);

                AlertDialog.Builder msgInsertProz = new AlertDialog.Builder(v.getContext());
                msgInsertProz.setTitle(R.string.txt_Title_insert_item)
                        .setView(layoutProz)
                        .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (builder != null) {
                                    builder.cancel();
                                }


                            }
                        })
                        .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText insertItem = (EditText) layoutProz.findViewById(R.id.insertItemSpinner);

                                String item = insertItem.getText().toString();

                                long dataset = getDatabase().insertProcess(item, ActiveApplication.getAnwendung());

                                if (dataset != -1) {
                                    Toast.makeText(InputActivity.this, getString(R.string.txt_Dataset_inserted), Toast.LENGTH_LONG).show();
                                }
                                createProcessSpinner();


                                builder.dismiss();


                            }
                        });

                builder = msgInsertProz.show();
            }
        });


//        Prozess, Modul Spinner erstellen
        createProcessSpinner();
        createModulSpinner();

//      Listener Spinner Auswahl
        OnProcedureItemInputListener onProcedureItemInputListener = new OnProcedureItemInputListener();
        OnModuleItemInputListener onModuleItemInputListener = new OnModuleItemInputListener();

//        Listener zuweisen
        v_Process.setOnItemSelectedListener(onProcedureItemInputListener);
        v_Modul.setOnItemSelectedListener(onModuleItemInputListener);


        Log.d(TAG, "Items in ProcCursor: " + procCursor);
        Log.d(TAG, "Items in ProcCursor: " + modulCursor);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void createModulSpinner() {

        try {
            //        Spinner zuweisen
            v_Modul = (Spinner) findViewById(R.id.input_Mod);

            modulCursor = database.queryModul(ActiveApplication.getAnwendung());
            spinAdapterModul = new SimpleCursorAdapter(this
                    , android.R.layout.simple_spinner_item,
                    modulCursor,
                    new String[]{TcTables.TX_MOD},
                    new int[]{android.R.id.text1},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
            );
            spinAdapterModul.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            v_Modul.setAdapter(spinAdapterModul);

//            spinAdapterModul.changeCursor(modulCursor);

        } catch (Exception E) {
            Log.e(TAG, "createModulSpinner: " + E + "noch kein Eintrag in Datenbank!");
        }

    }

    public void createProcessSpinner() {
        //        Spinner zuweisen
        try {
            v_Process = (Spinner) findViewById(R.id.input_Proz);

            procCursor = database.queryProc(ActiveApplication.getAnwendung());
            spinAdapterProcedure = new SimpleCursorAdapter(this
                    , android.R.layout.simple_spinner_item,
                    procCursor,
                    new String[]{TcTables.TX_PROC},
                    new int[]{android.R.id.text1},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
            );
            spinAdapterProcedure.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            v_Process.setAdapter(spinAdapterProcedure);
        } catch (Exception E) {
            Log.e(TAG, "createProcessSpinner: " + E + "noch kein Eintrag in Datenbank!");
        }
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
//                String in_Modul;

                long rowID;

                v_Report = (EditText) findViewById(R.id.input_Code);
                v_Beschreibung = (EditText) findViewById(R.id.input_Bes);
                v_Bezeichung = (EditText) findViewById(R.id.input_Bez);
//                v_Modul = (EditText) findViewById(R.id.input_Mod);
//                v_Process = (Spinner) findViewById(R.id.input_Proz);


                in_Report = v_Report.getText().toString().toUpperCase();
                in_Beschreibung = v_Beschreibung.getText().toString();
                in_Bezeichnug = v_Bezeichung.getText().toString();
//                in_Modul = v_Modul.getText().toString().toUpperCase();

                Log.d(TAG, "ausgew. Prozess: " + ActiveApplication.getProcess());

                rowID = database.insertTc(in_Appl, in_Report, in_Bezeichnug, in_Beschreibung, ActiveApplication.getModul(), ActiveApplication.getProcess());

                if (rowID == -1) {

                    Toast.makeText(InputActivity.this, R.string.txt_Dataset_already_exists, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InputActivity.this, R.string.txt_Dataset_inserted, Toast.LENGTH_SHORT).show();
                }

                Log.d(this.getLocalClassName(), "Datensatz ID: " + rowID + " eingefügt!");

                database.close();
                procCursor.close();
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


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Input Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://sap.schweifer.at.tcode/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Input Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://sap.schweifer.at.tcode/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
