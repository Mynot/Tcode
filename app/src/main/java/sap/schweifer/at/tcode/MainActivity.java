package sap.schweifer.at.tcode;

import android.content.Intent;
import android.database.Cursor;
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

import sap.schweifer.at.database.TcDatabase;
import sap.schweifer.at.database.TcTables;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private CursorAdapter ca;
    private int anzahlDatensaetze;
    public CodeObjects[] applItems = null;
    public ArrayAdapterItem mainItemAdapter = null;

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




    }

    public void insertDbObjects() {

        TcDatabase db = new TcDatabase(getApplicationContext());

        Cursor cursorApplication = db.query();

        anzahlDatensaetze = cursorApplication.getCount();

        applItems = new CodeObjects[anzahlDatensaetze];

        int arrayLength = 0;

        arrayLength = applItems.length;

        Log.i(TAG, "Es sind " + anzahlDatensaetze + " gespeichert!");

        int i = 0;


        if (cursorApplication != null && cursorApplication.moveToFirst() &&
                anzahlDatensaetze != arrayLength
            ) {
            while (!cursorApplication.isAfterLast()) {
                applItems[i] = new CodeObjects(
                        cursorApplication.getInt(cursorApplication.getColumnIndexOrThrow(TcTables.ID)),
                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_APPLICATION)),
                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_REPORT)),
                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_BES)),
                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_BEZ)),
                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_MOD)),
                        cursorApplication.getString(cursorApplication.getColumnIndex(TcTables.TX_PROC))

                );
                i = ++i;
                Log.i(TAG, "Es sind " + i + " Datensätze im Array!");
                cursorApplication.moveToNext();


            }
        }



        ArrayAdapterItem mainItemAdapter = new ArrayAdapterItem(this, R.layout.rel_datenbankeintrag, applItems);

//        SimpleCursorAdapter test = new SimpleCursorAdapter(this,R.layout.rel_datenbankeintrag,cursorApplication, );

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
}
