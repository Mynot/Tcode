package sap.schweifer.at.tcode;

/**
 * Created by Stefan on 11.02.2016.
 */
public class ActiveApplication {


    private static String Anwendung;

    private static String Process;

    private static String Modul;


    private static String serchProcess = "";


    public ActiveApplication() {
//Standard Konstruktor
    }

    public static String getAnwendung() {
        return Anwendung;
    }

    public static void setAnwendung(String anwendung) {
        Anwendung = anwendung;
    }

    public static String getProcess() {
        return Process;
    }

    public static void setProcess(String process) {
        Process = process;
    }


    public static String getModul() {
        return Modul;
    }

    public static void setModul(String modul) {
        Modul = modul;
    }

    public static String getSerchProcess() {
        return serchProcess;
    }

    public static void setSerchProcess(String serchProcess) {
        ActiveApplication.serchProcess = serchProcess;
    }
}