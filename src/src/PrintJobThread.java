package src;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
    PrintJobThread handles printing an entire print request.
    It must get a free printer (blocking if the are all busy) then read sectors from the disk and
    send to the printer - one at a time.
    A UserThread will create and start a new PrintJobThread for each print request.
    Note if each PrintJob is not a new thread you will get very little parallelism in your OS.
 */
public class PrintJobThread extends Thread {
    private int printerID = 0;
    private String printJobID;
    private String fileName;
    private FileInfo fileInfo;

    public PrintJobThread(String printJobID, String fileName, FileInfo fileInfo){
        super(printJobID);
        this.printJobID = printJobID;
        this.fileName = fileName;
        this.fileInfo = fileInfo;
    }

    @Override
    public void run(){

        //Get available printer
        this.printerID = OperatingSystem.printerManager.request(); //Semaphore
        Printer printer = OperatingSystem.printerArray[this.printerID];

        //JavaFX
        if(Main.runGUI){
            Main.printerActive[this.printerID].setFill(Color.RED);
        }

        //Get file information
        int startingSector = this.fileInfo.getStartingSector();
        int fileLength = this.fileInfo.getFileLength();
        int diskNumber = this.fileInfo.getDiskNumber();

        Disk disk = OperatingSystem.diskArray[diskNumber];
        int id= this.printerID+1;
        String printerFileName = "PRINTER"+id;
        File file = new File(printerFileName);
        // creates the file
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // creates FileWriter and print to file
        FileWriter fw = null;
        try {
            String printJobStatus = this.printJobID;
            System.out.println(printJobStatus);
            //JavaFX
            if(Main.runGUI){
                Main.printerStatusText[this.printerID].setText(printJobStatus);
            }
            fw = new FileWriter(file, true);

            //get each string buffer and print it to file
            for(int index = 0; index<fileLength; index++){
                StringBuffer data = disk.read(startingSector+index);
                printer.print(fw, data );
                //JavaFX
                /**/if(Main.runGUI){
                    Main.printerStatusText[this.printerID].setText(data.toString());
                }
            }

            fw.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        //System.out.println("Printed file " + this.fileName + " on printer# " + this.printerID);
        String status = "Printed file " + this.fileName + " on printer#" + printerFileName;
        System.out.println(status);

        //JavaFX
        if(Main.runGUI){
            Main.printerStatusText[this.printerID].setText(status);
            Main.printerActive[this.printerID].setFill(Color.LIGHTGREEN);
            Main.printerStatusText[this.printerID].setText("Idle"); //clear status text
        }

        //release printer resource
        OperatingSystem.printerManager.release(this.printerID);

    } //end of run

} //end of PrintJobThread
