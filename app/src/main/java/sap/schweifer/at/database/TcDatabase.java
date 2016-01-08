package sap.schweifer.at.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Stefan on 12.10.2015.
 */
public class TcDatabase extends SQLiteOpenHelper {
    private static final String TAG = TcDatabase.class.getSimpleName();

    public static final int TcVersion = 1;
    public static final String TcDBName = "tCode.db";


    public TcDatabase(Context context) {
        super(context, TcDBName, null, TcVersion);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TcTables.CREATE_TABLE_TC);
        db.execSQL(TcTables.CREATE_TABLE_APPL);
        db.execSQL(TcTables.CREATE_TABLE_REPO);
        db.execSQL(TcTables.CREATE_TABLE_MOD);
//        db.execSQL(TcTables.CREATE_TABLE_PROC);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Neue Datenbankversion " + newVersion + " alle Daten von Version " + oldVersion + " werden gelöscht!");
        db.execSQL(TcTables.DROP_TABLE_APPL);
        db.execSQL(TcTables.DROP_TABLE_CODE);
        db.execSQL(TcTables.DROP_TABLE_REPO);
        db.execSQL(TcTables.DROP_TABLE_MOD);
//        db.execSQL(TcTables.DROP_TABLE_PROC);
    }

    //Daten in Tabelle Codes schreiben
    public long insertTc(String appl,
                         String report,
                         String bez,
                         String bes,
                         String mod,
                         String proz
    ) {
        long rowId = -1;
        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //Daten schreiben
            ContentValues values = new ContentValues();

            values.put(TcTables.TX_APPLICATION, appl);
            values.put(TcTables.TX_REPORT, report);
            values.put(TcTables.TX_BEZ, bez);
            values.put(TcTables.TX_BES, bes);
            values.put(TcTables.TX_MOD, mod);
            values.put(TcTables.TX_PROC, proz);

            rowId = db.insert(TcTables.CODE_TABLE, null, values);


        } catch (Exception e) {
            Log.e(TAG, "Insert() " + TcTables.CODE_TABLE, e);
        } finally {
            Log.d(TAG, "insert() " + TcTables.CODE_TABLE + " " + rowId);
        }
        return rowId;
    }


    public void insertApplication(String application) {
        long rowId = -1;
        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //Daten schreiben
            ContentValues values = new ContentValues();

            values.put(TcTables.TX_APPLICATION, application);

            rowId = db.insert(TcTables.APPLICATION_TABLE, null, values);

        } catch (Exception e) {
            Log.e(TAG, "Insert() " + TcTables.APPLICATION_TABLE, e);
        } finally {
            Log.d(TAG, "insert() " + TcTables.APPLICATION_TABLE + " " + rowId);
        }
    }

    public void insertReport(String report) {
        long rowId = -1;
        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //Daten schreiben
            ContentValues values = new ContentValues();

            values.put(TcTables.TX_REPORT, report);

            rowId = db.insert(TcTables.REPORT_TABLE, null, values);

        } catch (Exception e) {
            Log.e(TAG, "Insert() " + TcTables.REPORT_TABLE, e);
        } finally {
            Log.d(TAG, "insert() " + TcTables.REPORT_TABLE + " " + rowId);
        }

    }

    public void insertModul(String modul) {
        long rowId = -1;
        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //Daten schreiben
            ContentValues values = new ContentValues();

            values.put(TcTables.TX_MOD, modul);

            rowId = db.insert(TcTables.MODUL_TABLE, null, values);

        } catch (Exception e) {
            Log.e(TAG, "Insert() " + TcTables.MODUL_TABLE, e);
        } finally {
            Log.d(TAG, "insert() " + TcTables.MODUL_TABLE + " " + rowId);
        }
    }
//
//    public void insertProcess (String process)
//    {
//        long rowId = -1;
//        try {
//            //Datenbank öffnen
//            SQLiteDatabase db = getWritableDatabase();
//            Log.d(TAG, "Pfad: " + db.getPath());
//
//            //Daten schreiben
//            ContentValues values = new ContentValues();
//
//            values.put(TcTables.TX_PROC,process);
//
//            rowId = db.insert(TcTables.PROCEDURES_TABLE,null,values);
//
//        } catch (Exception e) {
//            Log.e(TAG,"Insert() "+TcTables.PROCEDURES_TABLE, e );
//        } finally {
//            Log.d(TAG, "insert() "+TcTables.PROCEDURES_TABLE+" "+ rowId);
//        }
//    }

    //Daten aus Tabelle Codes auslesen
    public Cursor query() {

        //Datenbank öffnen
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TcTables.CODE_TABLE,
                null,
                null,
                null,
                null,
                null, TcTables.TX_REPORT + " DESC");


    }

    //Daten aus Tabelle Applications auslesen
    public Cursor queryAPPL() {

        //Datenbank öffnen
        try {
            SQLiteDatabase db = getWritableDatabase();
            return db.query(TcTables.APPLICATION_TABLE,
                    null,
                    null,
                    null,
                    null,
                    null, TcTables.TX_APPLICATION + " DESC");
        } catch (Exception e) {
            Log.e(TAG, "query" + TcTables.APPLICATION_TABLE + "keine Daten!");
            return null;
        } finally {
            Log.i(TAG, "query" + TcTables.APPLICATION_TABLE + "erfolgreich!");
//            return null;
        }


    }


}
