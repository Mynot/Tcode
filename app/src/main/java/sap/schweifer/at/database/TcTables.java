package sap.schweifer.at.database;

/**
 * Created by sschweif on 13.10.2015.
 */
public class TcTables {
    private static final String TAG = TcTables.class.getSimpleName();

    //Datenbank Tabellen

    public static final String CODE_TABLE = "tCode";
    public static final String APPLICATION_TABLE = "tAppl";
    public static final String REPORT_TABLE = "tRepo";
    public static final String MODUL_TABLE = "tMod";
    public static final String PROCEDURES_TABLE = "tProc";

    // Datenbank Felder
    public static final String ID = "_id";
    public static final String TX_APPLICATION = "tx_Appl";
    public static final String TX_REPORT = "tx_Report";
    public static final String TX_BEZ = "tx_Bezeichnung";
    public static final String TX_BES = "tx_Beschreibung";
    public static final String TX_MOD = "tx_Modul";
    public static final String TX_PROC = "tx_Prozess";

    //Datenbank Tabelle CODE anlegen
    public static final String CREATE_TABLE_TC = "CREATE TABLE " + CODE_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TX_APPLICATION + " TEXT NOT NULL, " +
            TX_REPORT + " TEXT UNIQUE NOT NULL, " +
            TX_BEZ + " TEXT NOT NULL, " +
            TX_BES + " TEXT, " +
            TX_MOD + " TEXT NOT NULL, " +
            TX_PROC + " TEXT NOT NULL)";

    // Tabelle Anwendungen anlegen
    public static final String CREATE_TABLE_APPL = "CREATE TABLE " + APPLICATION_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TX_APPLICATION + " TEXT UNIQUE NOT NULL)";

    // Tabelle Reports anlegen
    public static final String CREATE_TABLE_REPO = "CREATE TABLE " + REPORT_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TX_APPLICATION + " TEXT NOT NULL, " +
            TX_REPORT + " TEXT UNIQUE NOT NULL)";

    // Tabelle Module anlegen
    public static final String CREATE_TABLE_MOD = "CREATE TABLE " + MODUL_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TX_APPLICATION + " TEXT NOT NULL, " +
            TX_MOD + " TEXT UNIQUE NOT NULL)";

    // Tabelle Prozesse anlegen
    public static final String CREATE_TABLE_PROC = "CREATE TABLE " + PROCEDURES_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TX_APPLICATION + " TEXT NOT NULL, " +
            TX_PROC + " TEXT UNIQUE NOT NULL)";

    //Datenbank Tabelle löschen
    public static final String DROP_TABLE_CODE = "DROP TABLE IF EXISTS " + CODE_TABLE;
    public static final String DROP_TABLE_APPL = "DROP TABLE IF EXISTS " + APPLICATION_TABLE ;
    public static final String DROP_TABLE_REPO = "DROP TABLE IF EXISTS " + REPORT_TABLE;
    public static final String DROP_TABLE_MOD = "DROP TABLE IF EXISTS " + MODUL_TABLE;
    public static final String DROP_TABLE_PROC = "DROP TABLE IF EXISTS " + PROCEDURES_TABLE;

}
