package sap.schweifer.at.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import sap.schweifer.at.tcode.R;

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
        db.execSQL(TcTables.CREATE_TABLE_PROC);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Neue Datenbankversion " + newVersion + " alle Daten von Version " + oldVersion + " werden gelöscht!");
        db.execSQL(TcTables.DROP_TABLE_APPL);
        db.execSQL(TcTables.DROP_TABLE_CODE);
        db.execSQL(TcTables.DROP_TABLE_REPO);
        db.execSQL(TcTables.DROP_TABLE_MOD);
        db.execSQL(TcTables.DROP_TABLE_PROC);
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

            Log.d(TAG, R.string.txt_Dataset_already_exists + " | " + e);

        } finally {
            Log.d(TAG, "insert() " + TcTables.CODE_TABLE + " " + rowId);
        }
        return rowId;
    }


    public long insertApplication(String application) {
        long rowId = -1;
        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //Daten schreiben
            ContentValues values = new ContentValues();

            values.put(TcTables.TX_APPLICATION, application);

            rowId = db.insert(TcTables.APPLICATION_TABLE, null, values);

        } catch (SQLiteConstraintException e) {
            Log.e(TAG, "Insert() " + TcTables.APPLICATION_TABLE, e);
        } finally {
            Log.d(TAG, "insert() " + TcTables.APPLICATION_TABLE + " " + rowId);
        }
        return rowId;
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

    public long insertModul(String modul, String appl) {
        long rowId = -1;
        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //Daten schreiben
            ContentValues values = new ContentValues();

            values.put(TcTables.TX_MOD, modul);
            values.put(TcTables.TX_APPLICATION, appl);

            rowId = db.insert(TcTables.MODUL_TABLE, null, values);

        } catch (Exception e) {
            Log.e(TAG, "Insert() " + TcTables.MODUL_TABLE, e);
            return 0;
        } finally {
            Log.d(TAG, "insert() " + TcTables.MODUL_TABLE + " " + rowId);
            return rowId;
        }
    }

    public long insertProcess(String process, String appl) {
        long rowId = -1;
        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //Daten schreiben
            ContentValues values = new ContentValues();

            values.put(TcTables.TX_PROC, process);
            values.put(TcTables.TX_APPLICATION, appl);

            rowId = db.insert(TcTables.PROCEDURES_TABLE, null, values);

        } catch (Exception e) {
            Log.e(TAG, "Insert() " + TcTables.PROCEDURES_TABLE, e);
            return 0;
        } finally {
            Log.d(TAG, "insert() " + TcTables.PROCEDURES_TABLE + " " + rowId);
            return rowId;
        }

    }

    //Daten in Tabelle Codes aktualisieren
    public long updateTc(int ID,
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

            //Daten aktualisieren
            // TODO: 14.01.2016 Daten aus View mit ID aus Tag auslesen

            ContentValues values = new ContentValues();


            values.put(TcTables.TX_REPORT, report);
            values.put(TcTables.TX_BEZ, bez);
            values.put(TcTables.TX_BES, bes);
            values.put(TcTables.TX_MOD, mod);
            values.put(TcTables.TX_PROC, proz);


            rowId = db.update(TcTables.CODE_TABLE,
                    values,
                    TcTables.ID + "=?",
                    new String[]{String.valueOf(ID)});

        } catch (Exception e) {
            Log.e(TAG, "update() " + TcTables.CODE_TABLE, e);
            return 0;
        } finally {
            Log.d(TAG, "update() " + TcTables.CODE_TABLE + " " + rowId);
            return rowId;
        }

    }


    //Daten aus Tabelle Codes auslesen
    public Cursor query(String searchAppl, String searchProc) {

        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            return db.query(TcTables.CODE_TABLE,
                    null,
                    TcTables.TX_APPLICATION + " =? and " + TcTables.TX_REPORT + " LIKE ? ",
                    new String[]{searchAppl, searchProc + "%"},
                    null,
                    null,
                    TcTables.TX_APPLICATION + ", "
                            + TcTables.TX_REPORT + ", "
                            + TcTables.TX_PROC + ", " + TcTables.TX_MOD + " ASC");
        } catch (Exception e) {
            Log.e(TAG, "query: " + TcTables.CODE_TABLE + e + " " + " Fehler!");

            return null;

        } finally {
            Log.i(TAG, "query: " + TcTables.CODE_TABLE + " " + " Erfolg!");
        }

    }

    //Daten aus Tabelle Codes auslesen
    public Cursor queryDatasetAppl(String searchID) {

        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            return db.query(TcTables.CODE_TABLE,
                    null,
                    TcTables.ID + " =? ",
                    new String[]{searchID},
                    null,
                    null,
                    null);
        } catch (Exception e) {
            Log.e(TAG, "queryDatasetAppl: " + TcTables.CODE_TABLE + e + " " + searchID + " Fehler!");
            return null;
        } finally {
            Log.i(TAG, "queryDatasetAppl: " + TcTables.CODE_TABLE + " " + searchID + " Erfolg!");
        }

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
            Log.e(TAG, "queryAPPL" + TcTables.APPLICATION_TABLE + "keine Daten!", e);
            return null;
        } finally {
            Log.i(TAG, "queryAPPL" + TcTables.APPLICATION_TABLE + "erfolgreich!");
//            return null;
        }


    }

    //Daten aus Tabelle Prozeduren auslesen
    public Cursor queryProc(String l_appl) {

        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();

            return db.query(TcTables.PROCEDURES_TABLE,
                    null,
                    TcTables.TX_APPLICATION + " =? ",
                    new String[]{l_appl},
                    null,
                    null,
                    null);
        } catch (Exception e) {
            Log.e(TAG, "queryProc: " + TcTables.PROCEDURES_TABLE + e + " " + " Fehler Z301!", e);
            return null;
        } finally {
            Log.i(TAG, "queryProc: " + TcTables.PROCEDURES_TABLE + " " + " Erfolg!");
        }

    }

    //Daten aus Tabelle Prozeduren auslesen
    public Cursor queryProcRaw() {

        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();

            return db.rawQuery("SELECT 0 , XXX , Prozedur ",

//                    "SELECT 0 as " + TcTables.ID + ", XXX AS " + TcTables.TX_APPLICATION + ", Prozedur AS " + TcTables.TX_PROC,
//                            " UNION" +
//                            " SELECT " + TcTables.ID + ", " + TcTables.TX_APPLICATION + ", " + TcTables.TX_PROC +
//                            " from " + TcTables.PROCEDURES_TABLE,
                    null);
        } catch (Exception e) {
            Log.e(TAG, "queryProc: " + TcTables.PROCEDURES_TABLE + e + " " + " Fehler Z301!", e);
            return null;
        } finally {
            Log.i(TAG, "queryProc: " + TcTables.PROCEDURES_TABLE + " " + " Erfolg!");
        }

    }


    //Daten aus Tabelle Prozeduren auslesen
    public Cursor queryModul(String l_appl) {

        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();


            return db.query(TcTables.MODUL_TABLE,
                    null,
                    TcTables.TX_APPLICATION + " =? ",
                    new String[]{l_appl},
                    null,
                    null,
                    null);
        } catch (Exception e) {
            Log.e(TAG, "queryProc: " + TcTables.MODUL_TABLE + e + " " + " Fehler!");
            return null;
        } finally {
            Log.i(TAG, "queryProc: " + TcTables.MODUL_TABLE + " " + " Erfolg!");
        }

    }


    //Datensatz aus Tabelle Prozeduren auslesen
    public Cursor queryProcSet(String searchProc) {

        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            return db.query(TcTables.PROCEDURES_TABLE,
                    new String[]{TcTables.TX_PROC},
                    TcTables.TX_PROC + " =? ",
                    new String[]{searchProc},
                    null,
                    null,
                    null);
        } catch (Exception e) {
            Log.e(TAG, "queryProcSet: " + TcTables.TX_PROC + " " + " Fehler!");
            return null;
        } finally {
            Log.i(TAG, "queryProcSet: " + TcTables.TX_PROC + " " + " Erfolg!");
        }

    }

    //Daten aus Tabelle Codes löschen
    public int deleteDataset(String searchID) {

        try {
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            return db.delete(TcTables.CODE_TABLE,
                    TcTables.ID + " =? ",
                    new String[]{searchID}
            );
        } catch (Exception e) {
            Log.e(TAG, "deleteDataset: " + TcTables.CODE_TABLE + e + " " + searchID + " Fehler Z 343!");
            return 0;
        } finally {
            Log.i(TAG, "deleteDataset: " + TcTables.CODE_TABLE + " " + searchID + " erfolgreich gelöscht!");
        }

    }


}
