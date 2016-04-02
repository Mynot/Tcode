package sap.schweifer.at.tcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
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

import sap.schweifer.at.database.TcDatabase;
import sap.schweifer.at.database.TcTables;
import sap.schweifer.at.tcode.Listener.OnModuleItemInputListener;
import sap.schweifer.at.tcode.Listener.OnProcedureItemInputListener;
import sap.schweifer.at.tcode.Listener.OnUpdateButtonClicklisterner;

public class UpdateActivity extends AppCompatActivity {
    static final String TAG = UpdateActivity.class.getSimpleName();


    //Cursor für das auslesen der Proceduren initialisieren
    private Cursor procCursor = null;
    private Cursor modulCursor = null;

    // Adapter für den Procedure Sinner
    SimpleCursorAdapter spinAdapterProcedure = null;
    SimpleCursorAdapter spinAdapterModul = null;


    //    Zu ändernder Datensatz
    private int in_DatasetID;
    //    Aktuelle Anwendung


    //    Edit Text instanziieren und initialisieren
    EditText v_Report = null;
    EditText v_Beschreibung = null;
    EditText v_Bezeichung = null;
    Spinner v_Modul = null;
    Spinner v_Process = null;

    //  Image Button instanziieren und initialisieren
    ImageButton but_modItem_add = null;
    ImageButton but_prozItem_add = null;


    // Datenbank instanziieren und initialisieren
    private TcDatabase database = null;


    String txt_Report = "";
    String txt_Beschreibung = "";
    String txt_Bezeichung = "";
    String txt_Modul = "";
    String txt_Prozess = "";

    private LayoutInflater insertAlertInlator;

    //   Alert Dialog
    AlertDialog builder;

    public TcDatabase getDatabase() {
        return database;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(ActiveApplication.getAnwendung());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_delete_data);
        Log.i(this.getLocalClassName(), "Activity gestartet");

        //Extras aus Bundle abfragen
        Bundle bundle = getIntent().getExtras();

        in_DatasetID = bundle.getInt("cID");
        Log.d(TAG, "onCreate ID aus Extras: " + in_DatasetID);

        String in_Appl = ActiveApplication.getAnwendung();
        Log.d(TAG, "aktuelle Anwendung: " + in_Appl);

        //Views zuweisen
        this.v_Report = (EditText) findViewById(R.id.input_Code);
        this.v_Beschreibung = (EditText) findViewById(R.id.input_Bes);
        this.v_Bezeichung = (EditText) findViewById(R.id.input_Bez);
        this.v_Modul = (Spinner) findViewById(R.id.input_Mod);
        this.v_Process = (Spinner) findViewById(R.id.input_Proz);

        // Button zuweisen
        this.but_prozItem_add = (ImageButton) findViewById(R.id.but_proz_add);
        this.but_modItem_add = (ImageButton) findViewById(R.id.but_mod_add);

        //Actionbar aktivieren


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Button zuweisen
        this.but_modItem_add = (ImageButton) findViewById(R.id.but_mod_add);
        this.but_prozItem_add = (ImageButton) findViewById(R.id.but_proz_add);

//        Button OnClick Listener Instanziieren
        OnUpdateButtonClicklisterner onUpdateItemClicklistener = new OnUpdateButtonClicklisterner();

//        OnClicklistener zuweisen
//        but_modItem_add.setOnClickListener(onUpdateItemClicklistener);
//        but_prozItem_add.setOnClickListener(onUpdateItemClicklistener);

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
                                    Toast.makeText(UpdateActivity.this, getString(R.string.txt_Dataset_inserted), Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(UpdateActivity.this, getString(R.string.txt_Dataset_inserted), Toast.LENGTH_LONG).show();
                                }
                                createProcessSpinner();


                                builder.dismiss();


                            }
                        });

                builder = msgInsertProz.show();
            }
        });


//      Datenbank zuweisen und Daten zum Anwendungs Datensatz auslesen
        database = new TcDatabase(this);
        Cursor c = database.queryDatasetAppl(String.valueOf(in_DatasetID));
        c.moveToFirst();

//      Werte aus Datenbank abrufen
        txt_Report = c.getString(c.getColumnIndex(TcTables.TX_REPORT));
        txt_Beschreibung = c.getString(c.getColumnIndex(TcTables.TX_BES));
        txt_Bezeichung = c.getString(c.getColumnIndex(TcTables.TX_BEZ));
        txt_Modul = c.getString(c.getColumnIndex(TcTables.TX_MOD));
        txt_Prozess = c.getString(c.getColumnIndex(TcTables.TX_PROC));

        Log.d(TAG, "onCreate: " + txt_Report);


        //Prozess Spinner erstellen
        createProcessSpinner();
        createModulSpinner();

        //Listener Spinner Auswahl
        OnProcedureItemInputListener onProcedureItemInputListener = new OnProcedureItemInputListener();
        OnModuleItemInputListener onModuleItemInputListener = new OnModuleItemInputListener();


        Log.d(TAG, "Items in ProcCursor: " + procCursor.getCount());
        Log.d(TAG, "Items in ModulCursor: " + modulCursor.getCount());

//        Listener zuweisen
        v_Modul.setOnItemSelectedListener(onModuleItemInputListener);
        v_Process.setOnItemSelectedListener(onProcedureItemInputListener);


//      Spinner Item Position ermitteln
        int toProcSelectedPosition = getProcSpinnerPosition(txt_Prozess, procCursor.getCount());
        int toModulSelectedPosition = getModulSpinnerPosition(txt_Modul, modulCursor.getCount());

//      Werte in  Textview schreiben
        v_Report.setText(txt_Report);
        v_Beschreibung.setText(txt_Beschreibung);
        v_Bezeichung.setText(txt_Bezeichung);


//        Position des Items im Spinner setzen
        if (toModulSelectedPosition != 0) {
            v_Modul.setSelection(toModulSelectedPosition);
        }

        if (toProcSelectedPosition != 0) {
            v_Process.setSelection(toProcSelectedPosition);
        }

    }

    private int getProcSpinnerPosition(String itemToSet, int itemsInSpinner) {
        int position;
        Log.d(TAG, "itemToSet: " + itemToSet);
        procCursor.moveToFirst();

        for (position = 0; position < itemsInSpinner; position++) {

            Log.d(TAG, "ermittleter String: " + procCursor.getString(procCursor.getColumnIndex(TcTables.TX_PROC)));

            if (procCursor.getString(procCursor.getColumnIndex(TcTables.TX_PROC)).equals(itemToSet)) {

                Log.d(TAG, "ermittlete Position: " + position + procCursor.getString(procCursor.getColumnIndex(TcTables.TX_PROC)));
                break;

            }

            procCursor.moveToNext();
        }

        return position;

    }

    private int getModulSpinnerPosition(String itemToSet, int itemsInSpinner) {
        int position;
        Log.d(TAG, "itemToSet: " + itemToSet);
        modulCursor.moveToFirst();

        for (position = 0; position < itemsInSpinner; position++) {

            Log.d(TAG, "ermittleter String: " + modulCursor.getString(modulCursor.getColumnIndex(TcTables.TX_MOD)));

            if (modulCursor.getString(modulCursor.getColumnIndex(TcTables.TX_MOD)).equals(itemToSet)) {

                Log.d(TAG, "ermittlete Position: " + position + modulCursor.getString(modulCursor.getColumnIndex(TcTables.TX_MOD)));
                break;

            }


            modulCursor.moveToNext();
        }

        return position;

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
        } catch (Exception E) {
            Log.e(TAG, "createModulSpinner: " + E + "noch kein Eintrag in Datenbank!");
        }

    }

    public void createProcessSpinner() {
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
                in_Modul = ActiveApplication.getModul();
                in_Process = ActiveApplication.getProcess();


                rowID = database.updateTc(in_DatasetID, in_Report, in_Bezeichnung, in_Beschreibung, in_Modul, in_Process);
                Log.i(this.getLocalClassName(), "Datensatz ID: " + rowID + " aktualisierent!");

                database.close();

                this.finish();

                return true;
//          Case delete: Datensatz wird gelöscht
            case R.id.tc_delete:

                rowID = database.deleteDataset(String.valueOf(in_DatasetID));
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
