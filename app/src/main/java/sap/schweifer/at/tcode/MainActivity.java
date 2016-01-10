package sap.schweifer.at.tcode;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import sap.schweifer.at.database.TcDatabase;
import sap.schweifer.at.database.TcTables;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private CursorAdapter ca;
    private int anzahlDatensaetze;
    public CodeObjects[] applItems = null;
    public SimpleCursorAdapter mainItemAdapter = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                startActivity(intent);
                //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //              .setAction("Action", null).show();

            }


        });


        insertDbObjects();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void insertDbObjects() {

        TcDatabase db = new TcDatabase(getApplicationContext());

        Cursor cursorApplication = db.query();

        anzahlDatensaetze = cursorApplication.getCount();

        applItems = new CodeObjects[anzahlDatensaetze];

//        int arrayLength = 0;
//
//        arrayLength = applItems.length;

        Log.i(TAG, "Es sind " + anzahlDatensaetze + " gespeichert!");

//        int i = 0;
//
//
//        if (cursorApplication != null && cursorApplication.moveToFirst()) {
//            while (!cursorApplication.isAfterLast()) {
//                applItems[i] = new CodeObjects(
//                        cursorApplication.getInt(cursorApplication.getColumnIndexOrThrow(TcTables.ID)),
//                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_APPLICATION)),
//                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_REPORT)),
//                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_BES)),
//                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_BEZ)),
//                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_MOD)),
//                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_PROC))
//
//                );
//                i = ++i;
//                Log.i(TAG, "Es sind " + i + " Datensätze im Array!");
//                cursorApplication.moveToNext();
//
//
//            }
//        }
//
//
//        mainItemAdapter = new ArrayAdapterItem(this, R.layout.rel_datenbankeintrag, applItems);


        mainItemAdapter = new SimpleCursorAdapter(this,
                R.layout.rel_datenbankeintrag,
                cursorApplication,
                new String[]{
                        TcTables.TX_APPLICATION,
                        TcTables.TX_REPORT,
                        TcTables.TX_BEZ,
                        TcTables.TX_BES,
                        TcTables.TX_MOD,
                        TcTables.TX_PROC},
                new int[]
                        {
                                R.id.txt_Appl,
                                R.id.txt_Code,
                                R.id.txt_Bezeichnung,
                                R.id.txt_Beschreibung,
                                R.id.txt_Modul,
                                R.id.txt_Prozess},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER

        );


        ListView list_Datenbankeintraege = (ListView) findViewById(R.id.lv_Datenbankeinträge);

        list_Datenbankeintraege.setAdapter(mainItemAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        insertDbObjects();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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
