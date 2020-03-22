package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
Each printer will write data to a file named PRINTERi where i is the index of this printer (starting at 1).
A printer can only handle one line of text at a time.
It will take 2750 milliseconds to print one line;
the thread needs to sleep to simulate this delay before the printing job finishes.
 */
public class Printer {

    private String printerName;

    public Printer(String printerName){
        this.printerName = printerName;
    }


    void print(FileWriter fw, StringBuffer data)  { // call sleep

        // write the content to the new file
        /**/BufferedWriter bw;
        try {
            bw = new BufferedWriter(fw);
            bw.write(data.toString());
            bw.newLine();
            bw.flush();
            //bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        try {
            Thread.sleep( 2750);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
