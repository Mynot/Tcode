package sap.schweifer.at.tcode;

/**
 * Created by sschweif on 20.10.2015.
 */
public class CodeObjects {

    public int ID;
    public String APPL;
    public String REPORT;
    public String BESCHREIBUNG;
    public String BEZEICHNUNG;
    public String MODUL;
    public String PROCESS;

    public CodeObjects (int ID,
                        String Appl,
                       String Report,
                       String Beschreibung,
                       String Bezeichung,
                       String Modul,
                       String Process){
                            this.ID = ID;
                            APPL = Appl;
                            REPORT = Report;
                            BESCHREIBUNG = Beschreibung;
                            BEZEICHNUNG = Bezeichung;
                            MODUL = Modul;
                            PROCESS= Process;
    }




}
