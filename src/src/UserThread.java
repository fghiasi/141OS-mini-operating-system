package src;

import javafx.scene.paint.Color;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Each user will read from a file named USERi where i is the index of this user.
Each USERi file will contain a series of the following three commands:
    .save X
    .end
    .print X
All lines between a .save and a .end is part of the data in file X.
Each line in the file takes up a sector in the disk.
UserThread will handle saving files to disk,
but it will create a new PrintJobThread to handle each print request.
Each UserThread may have only one local StringBuffer to hold the current line of text.
If you read a line from the input file,
you must immediately copy that String into this single StringBuffer owned by the UserThread.
The UserThread interprets the command in the StringBuffer or saves the StringBuffer
to a disk as appropriate for the line of input.

 */
public class UserThread extends Thread{

    private final int NAME_STARTING_INDEX_FOR_SAVE = 6;
    private final int NAME_STARTING_INDEX_FOR_PRINT = 7;


    private String userName;
    private int userID = 0;
    private int diskID = 0;
    private StringBuffer fileName;
    private StringBuffer currentLine = null;

    public UserThread( String userName, int userID){
        super(userName);
        this.userName = userName;
        this.userID = userID;
    }

    //TODO
    //write code to read file "USERindex"

    @Override
    public void run(){

        //read file
        File f = new File("inputs/" + this.userName); //TODO later
        FileReader fr = null;
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        BufferedReader br;
        try {
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line !=null) {
                //System.out.println(line); //TODO for debugging

                //save
                if(line.startsWith(".save")) { //beginning of file

                    //get file name
                    this.fileName = new StringBuffer(line.substring(NAME_STARTING_INDEX_FOR_SAVE));

                    //get any available disk
                    this.diskID = OperatingSystem.diskManager.request();  //Semaphore

                    //Java FX
                    if(Main.runGUI){
                        Main.diskActive[this.diskID].setFill(Color.RED);
                    }

                    String status = this.userName+" is saving to disk#"+this.diskID;
                    //System.out.println("Debug: "+this.userName+" is saving to disk#"+this.diskID ); //TODO Debug
                    System.out.println(status);
                    if(Main.runGUI){
                        Main.diskStatusText[this.diskID].setText(status);
                        Main.userStatusText[this.userID].setText(status);
                    }

                }

                //end
                else if(line.startsWith(".end")){  //end of file

                    String status = this.userName + " has saved file "+this.fileName+ " to disk#" + this.diskID;
                    System.out.println(status);

                    //Java FX
                    if(Main.runGUI){
                        Main.diskActive[this.diskID].setFill(Color.LIGHTGREEN);
                        Main.diskStatusText[this.diskID].setText(status);
                        //System.out.println("Debug: " + this.userName + " has saved file "+this.fileName+ " to disk#" + this.diskID);
                        Main.diskStatusText[this.diskID].setText("Idle"); //clear status text
                        Main.userStatusText[this.userID].setText(("")); //clear status text

                    }

                    //release disk resource
                    OperatingSystem.diskManager.release(this.diskID);

                    this.fileName = null;
                }

                //print
                else if(line.startsWith(".print")){

                    this.fileName = new StringBuffer(line.substring(NAME_STARTING_INDEX_FOR_PRINT));
                    FileInfo fileInfo = OperatingSystem.diskManager.getFileInfo(this.fileName);

                    //System.out.println("Reached print command at "+this.fileName);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    String printJobID = "printJobID: " + this.userName +
                            " "+ this.fileName + " "+ formatter.format(date);
                    PrintJobThread printJobThread = new PrintJobThread(printJobID, this.fileName.toString(), fileInfo);
                    printJobThread.start();

                    try {
                        printJobThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // write data to disk
                else if(this.fileName!=null){

                    this.currentLine = new StringBuffer(line);
                    FileInfo fileInfo = OperatingSystem.diskManager.getFileInfo(this.fileName);

                    //if null, create new file info
                    if(fileInfo == null){


                        int diskNumber = this.diskID;
                        //retrieve available disk
                        //Disk disk = Main.diskManager.getDisk(this.diskID);
                        Disk disk = OperatingSystem.diskArray[this.diskID];
                        if(disk==null){
                            System.out.println("disk is null");
                        } else {
                            int startingSector = disk.getNextFreeSector(); //TODO finish later
                            if(startingSector==-1){
                                throw new DiskFullException("disk " + disk.getDiskName() + " if full");
                            }
                            int fileLength = 1;  //1 StringBuffer
                            fileInfo =
                                    new FileInfo(
                                            diskNumber,
                                            startingSector,
                                            fileLength
                                    );
                            OperatingSystem.diskManager.saveFile(this.fileName, fileInfo); //store file info into diskManager
                            disk.write(fileInfo.getStartingSector(), this.currentLine);

                            //JavaFx
                            if(Main.runGUI){
                                //Main.diskStatusText[this.diskID].setText(this.currentLine.toString());
                            }

                        }


                    //otherwise, update fileInfo and get file's corresponding disk
                    } else {

                        //get disk given fileInfo
                        ///int diskNumber = fileInfo.getDiskNumber();
                        //this.diskID = this.diskManager.request(diskNumber); //Semaphore
                        //Disk disk = Main.diskManager.getDisk(this.diskID);
                        Disk disk = OperatingSystem.diskArray[this.diskID];
                        if(disk==null){
                            System.out.println("disk is null");
                        } else {
                            int sector = disk.getNextFreeSector(); //TODO finish later
                            if(sector==-1){
                                throw new DiskFullException("disk " + disk.getDiskName() + " if full");
                            }
                            int newFileLength = fileInfo.getFileLength() + 1;
                            fileInfo.setFileLength(newFileLength);
                            disk.write(sector,this.currentLine);

                            //JavaFx
                            if(Main.runGUI){
                                //Main.diskStatusText[this.diskID].setText(this.currentLine.toString());
                            }

                        }

                    }
                }

                this.currentLine = null;
                //read next line
                line = br.readLine();

                //Disk testDisk = Main.diskArray[this.diskID];

            } //end of while
            br.close();
            fr.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
        } catch(DiskFullException dfe){
            dfe.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        } */

    } //end of run()

} //end of UserThread
