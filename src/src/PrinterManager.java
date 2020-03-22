package src;

/*
    The PrinterManager is derived from ResourceManager.
 */
public class PrinterManager extends ResourceManager {


    //Singleton
    private static PrinterManager printerManager;
    private int numberOfItems;

    /*
        Use Singleton Design Pattern
     */
    private PrinterManager(int numberOfItems) {
        super(numberOfItems);
        this.numberOfItems = numberOfItems;
    }

    //Singleton
    public static PrinterManager getInstance(int numberOfItems){
        if(printerManager == null){
            printerManager = new PrinterManager(numberOfItems);
        }
        return printerManager;
    }




}
