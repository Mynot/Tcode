package sap.schweifer.at.tcode;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sap.schweifer.at.database.TcDatabase;

/**
 * Created by Stefan on 14.05.2016.
 * Klasse für das Backup der gesamten Datenbank
 * Die Datenbank soll im Downloadordner abgespeichert werden und von dort auch wieder herstellbar sein.
 */
public class BackupDatabase {

    public BackupDatabase() {


//        Standard Konstruktor
    }

    public void saveDatabase() throws IOException {
        final String inFilename = "/data/data/sap.schweifer.at.tcode/databases/" + TcDatabase.class.getName();
        File inFile = new File(inFilename);

        FileInputStream inFilestream = new FileInputStream(inFile);

        String outputFilename = Environment.getExternalStorageDirectory() + "/tCodeCopy.db";
        //open empty Database as the output File
        OutputStream output = new FileOutputStream(outputFilename);

//        write the input to the output Stream
        byte[] buffer = new byte[1042];
        int length;
        while ((length = inFilestream.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        inFilestream.close();

    }
}
