package sap.schweifer.at.tcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import sap.schweifer.at.database.TcDatabase;
import sap.schweifer.at.database.TcTables;

/**
 * Main Klasse für die Anzeige von Transaktionen in Form eine List Views.
 * Durch das tippen auf ein Element des Listviews wird die Activity zum bearbeiten eines Datensatzes aufgerufen.
 * Das klicken auf den Floating Action point ruft die Activity zum hinzufügen von Daten auf
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Cursor cursorApplication;
    private int anzahlDatensaetze = 0;

    //  Datenbank initialisieren
    TcDatabase database = new TcDatabase(this);

    //private String selApplication;

    //  Adapter initialisieren
    public SimpleCursorAdapter mainItemAdapter = null;
    //  Datenbank initialisieren


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//  Main List View initialisieren
    ListView list_Datenbankeintraege = null;


    //  Textview Hauptansicht Überschrift initialiseren
    TextView txtApplicationHead = null;

    private AlertDialog builder;

    //Menü für Titeländerung initialisieren
    Menu menu = null;


    private GoogleApiClient client;

//    public String getSelApplication() {
//        return selApplication;
//    }
//
//    public void setSelApplication(String selApplication) {
//        this.selApplication = selApplication;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(ActiveApplication.getAnwendung());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        //activity_main
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActiveApplication.getAnwendung() == null) {

//                  Snackbar wenn keine Anwendung gesetzt wurde
                    Snackbar.make(view, R.string.txt_keine_Anwendung_ausgewählt, Snackbar.LENGTH_LONG)
                            .setAction(R.string.txt_select_Appl, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    drawer.openDrawer(Gravity.LEFT);
                                }
                            }).show();

                } else {

//                  Input Activity starten wenn Anwendung gesetzt
                    Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                    startActivity(intent);
                }
            }


        });


        if (ActiveApplication.getAnwendung() == null) {
//            Wenn Activity null --> Drawer wird aufgerufen
            drawer.openDrawer(Gravity.LEFT);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        List View wird generiert und mit Daten gefüllt


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void loadView() {

        cursorApplication = database.query(ActiveApplication.getAnwendung(), ActiveApplication.getSerchProcess());

        if (cursorApplication != null) {
            anzahlDatensaetze = cursorApplication.getCount();
        }

        Log.i(TAG, "Es sind " + anzahlDatensaetze + " gespeichert!");


        if (anzahlDatensaetze != 0) {

            txtApplicationHead = (TextView) findViewById(R.id.txt_Head_Appl);

            txtApplicationHead.setText(ActiveApplication.getAnwendung());


            mainItemAdapter = new SimpleCursorAdapter(this,
                    R.layout.rel_datenbankeintrag,
                    cursorApplication,
                    new String[]{
                            TcTables.TX_REPORT,
                            TcTables.TX_REPORT,
                            TcTables.TX_BEZ,
                            TcTables.TX_BES,
                            TcTables.TX_MOD,
                            TcTables.TX_PROC},
                    new int[]
                            {R.id.txt_leading_letter_procedure,
                                    R.id.txt_Code,
                                    R.id.txt_Bezeichnung,
                                    R.id.txt_Beschreibung,
                                    R.id.txt_Modul,
                                    R.id.txt_Prozess},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER

            );


            list_Datenbankeintraege = (ListView) findViewById(R.id.lv_Datenbankeinträge);


            list_Datenbankeintraege.setAdapter(mainItemAdapter);

            // TODO: 24.01.2016 onItemclicklistener auslagern
            list_Datenbankeintraege.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //              Cursor des Adapters aufrufen
                    Cursor cursor = mainItemAdapter.getCursor();
                    //              Zur position des Cursors springen
                    cursor.moveToPosition(position);
                    //              ID des Datensatzes ermitteln
                    int codeID = cursor.getInt(cursor.getColumnIndex(TcTables.ID));

                    Log.d(TAG, "onItemClick: " + codeID);

                    //Intent erzeugen
                    Intent updateCode = new Intent(getApplicationContext(), UpdateActivity.class);
                    //Intent ID zuweisen
                    updateCode.putExtra("cID", codeID);
                    cursor.close();
                    //Activity starten
                    startActivity(updateCode);


                }
            });
        }


    }

    @Override
    protected void onResume() {
        setTitle(ActiveApplication.getAnwendung());
        super.onResume();

        if (ActiveApplication.getAnwendung() != null) {
            loadView();
        }

//        VectorDrawable ic_Circle = (VectorDrawable) getDrawable(R.drawable.ic_circle_48dp);
//        ic_Circle.setTint(getResources().getColor(R.color.colorAccent,getTheme()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        MenuItem searchItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ActiveApplication.setSerchProcess(query);
                Log.d(TAG, "onQueryTextSubmit: " + query);
                Log.d(TAG, "onQueryTextSubmit: " + ActiveApplication.getSerchProcess());

                if (mainItemAdapter == null) {
                    list_Datenbankeintraege.removeAllViews();
                    loadView();
                } else {
                    loadView();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ActiveApplication.setSerchProcess(newText);
                Log.d(TAG, "onQueryTextSubmit: " + newText);
                Log.d(TAG, "onQueryTextSubmit: " + ActiveApplication.getSerchProcess());

                if (mainItemAdapter == null) {
                    list_Datenbankeintraege.removeAllViews();
                    loadView();
                } else {
                    loadView();
                }
                return false;
            }
        });

        this.menu = menu;

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ActiveApplication.setAnwendung("SAP");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://sap.schweifer.at.tcode/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        database.close();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://sap.schweifer.at.tcode/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        final Cursor selectApplication = database.queryAPPL();
        if (id == R.id.nav_select_appl) {
//          Anwendung auswählen Dialog
            LayoutInflater selectAlertInlator = getLayoutInflater();


            final View layoutselAppl = selectAlertInlator.inflate(R.layout.box_select_item, null);


            final AlertDialog.Builder msgselectAppl = new AlertDialog.Builder(this);
            msgselectAppl.setTitle(R.string.txt_select_Appl)
                    .setView(layoutselAppl)
                    .setNegativeButton(R.string.btn_abbruch, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    builder.cancel();
                                }
                            }
                    );


            final ListView listselectedAppl = (ListView) layoutselAppl.findViewById(R.id.list_item_for_selection);
            final SimpleCursorAdapter cursorselectedAppl =
                    new SimpleCursorAdapter(this,
                            android.R.layout.simple_list_item_1,
                            selectApplication,
                            new String[]{TcTables.TX_APPLICATION},
                            new int[]{android.R.id.text1},
                            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                    );
            listselectedAppl.setAdapter(cursorselectedAppl);
            listselectedAppl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Cursor cursorLV = cursorselectedAppl.getCursor();

                    cursorLV.moveToPosition(position);

                    String selAppl = cursorLV.getString(cursorLV.getColumnIndex(TcTables.TX_APPLICATION));

                    ActiveApplication.setAnwendung(selAppl);
                    Log.d(TAG, "Anwendung ausgewählt: " + selAppl);
                    loadView();

                    MainActivity.this.finish();

                    Intent restartmain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(restartmain);


                    builder.dismiss();
                }
            });

            builder = msgselectAppl.show();
        }

        if (id == R.id.nav_new_appl) {
//          Anwendung einfügen Dialog

            LayoutInflater insertAlertInlator = getLayoutInflater();

            final View layout = insertAlertInlator.inflate(R.layout.box_insert_item, null);


            final AlertDialog.Builder msgInsertAppl = new AlertDialog.Builder(this);
            msgInsertAppl.setTitle(R.string.txt_Title_insert_item)
                    .setView(layout)
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

                            EditText insertItem = (EditText) layout.findViewById(R.id.insertItemSpinner);

                            String item = insertItem.getText().toString().toUpperCase();

                            ActiveApplication.setAnwendung(item);
                            long dataset = database.insertApplication(item);
                            Log.d(TAG, "Anwendung eingefügt: " + dataset);

                            MainActivity.this.finish();

                            Intent restartmain = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(restartmain);

                        }
                    })
                    .show();


        } else if (id == R.id.nav_search) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
