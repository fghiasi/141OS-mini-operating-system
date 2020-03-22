package src;

import java.util.ArrayList;
import java.util.List;

public class OperatingSystem {

    //Singleton
    private static OperatingSystem operatingSystem;

    public static Disk[] diskArray;
    public static Printer[] printerArray;
    public static DiskManager diskManager;
    public static PrinterManager printerManager;
    private int numOfDisk;
    private int numOfPrinters;;
    private List<UserThread> userThreadList;



    private OperatingSystem(int numOfDisk,
                            int numOfPrinters,
                            List<String> userList){
        //Disk
        this.diskArray = new Disk[numOfDisk];
        for(int index=0; index<numOfDisk; index++){
            this.diskArray[index] = new Disk("disk_"+index);
        }
        //DiskManager diskManager = new DiskManager( numOfDisk );
        //DiskManager diskManager = DiskManager.getInstance( numOfDisk );
        this.diskManager = DiskManager.getInstance( numOfDisk );

        //Printer
        this.printerArray = new Printer[numOfPrinters];
        for(int index=0; index<numOfPrinters; index++){
            this.printerArray[index] = new Printer("printer_"+index);
        }
        //PrinterManager printerManager = new PrinterManager( numOfPrinters );
        this.printerManager = PrinterManager.getInstance( numOfPrinters );

        //Initialize User Thread List
        this.userThreadList = new ArrayList<UserThread>();
        //User
        //create all user threads
        for(String user : userList){
            int userID = 0; //ID will be used to access array.
            if(user.equals("USER1")){
                userID = 0;
            } else if(user.equals("USER2")){
                userID = 1;
            } else if(user.equals("USER3")){
                userID = 2;
            } else if(user.equals("USER4")){
                userID = 3;
            }


            UserThread userThread =
                    new UserThread(user, userID);
            this.userThreadList.add( userThread );
        }
    }

    //Singleton
    public static OperatingSystem getInstance(int numOfDisk,
                                              int numOfPrinters,
                                              List<String> userList){
        if(operatingSystem == null){
            operatingSystem = new OperatingSystem(numOfDisk, numOfPrinters, userList);
        }
        return operatingSystem;
    }

    public void begin() throws InterruptedException{

        //start all user threads
        for(UserThread userThread : this.userThreadList){
            userThread.start();
        }
        //join all user threads
        /*for(UserThread userThread : this.userThreadList){
            userThread.join();
        }*/
    }

    //Display All Data In Disk
    public void displayAllDataInDisk(){
        for(int index=0; index<this.numOfDisk; index++){
            System.out.println("Disk#"+index+ " data:");
            this.diskArray[index].displayAllDataOnDisk();
            System.out.println("");
        }
    }



}
