package src;

/*
Each disk has a capacity specified to the constructor.
The constructor must allocate all the StringBuffers (one per sector)
when the Disk is created and must not allocate any after that.
You can only read or write one line at a time, so to store a file,
you must issue a write for each input line.
You may implement it as an array of StringBuffers.
Reads and writes each take 200 milliseconds,
so your read/write functions must sleep for that amount of time
before copying the data to/from any disk sector.

 */
public class Disk {
    private static final int NUM_SECTORS = 1024;
    private static final int SIZE_OF_STRING_BUFFER = 35;
    //StringBuffer sectors[] = new StringBuffer[NUM_SECTORS];
    private StringBuffer sectors[];
    private String diskName;
    private int nextFreeSector = 0;

    public Disk(String diskName) {
        this.sectors =  new StringBuffer[NUM_SECTORS]; //create 1024 sector array

        //for every element initialize a String Buffer object
        for(int i=0; i<sectors.length; i++){
            this.sectors[i] = new StringBuffer(SIZE_OF_STRING_BUFFER);
        }

        this.diskName = diskName;
    }

    public String getDiskName() {
        return diskName;
    }

    boolean write(int sector, StringBuffer data)   { //call sleep
        if(sector>this.NUM_SECTORS-1){
            System.out.println("Cannot write into disk " + this.diskName);
            System.out.println("disk " + this.diskName+ " is full." );
            return false;
        } else {
            this.sectors[sector].append(data);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.nextFreeSector++;
            return true;
        }
    }

    //StringBuffer read(int sector, StringBuffer data)
    StringBuffer read(int sector){  //call sleep
        //TODO later
        //System.out.println(sectors[sector]);
        try {
            Thread.sleep( 200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sectors[sector];
    }

    public int getNextFreeSector() {
        if(this.nextFreeSector>this.NUM_SECTORS-1){
            System.out.println("disk " + this.diskName + " is full.");
            return -1;
        } else {
            return this.nextFreeSector;
        }

    }

    public void setNextFreeSector(int nextFreeSector) {
        this.nextFreeSector = nextFreeSector;
    }

    public void displayAllDataOnDisk(){
        for(int i=0; i<sectors.length; i++){
            if(sectors[i].length()>0){ //string buffer is not empty
                System.out.println(sectors[i]);
            }
        }
    }
}
