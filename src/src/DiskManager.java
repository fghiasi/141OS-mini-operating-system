package src;

/*
    The DiskManager is derived from ResourceManager (given below) and
    also keeps track of the next free sector on each disk, which is useful for saving files.
    The DiskManager should contain the DirectoryManager for finding file sectors on Disk.
 */
public class DiskManager extends ResourceManager {

    //Singleton
    private static DiskManager diskManager;

    private DirectoryManager directoryManager;
    //private List<Disk> diskList;
    //private diskList[];
    //private Hashtable<Integer, Disk> diskTable = new Hashtable<Integer, Disk>();
    private int numberOfItems;

    /*
        Change to Singleton //use Singleton Design Pattern
     */
    private DiskManager(int numberOfItems) {
        super(numberOfItems);

        this.numberOfItems = numberOfItems;

        //create directory manager
        this.directoryManager = new DirectoryManager();

        //create disk list
        /*for(int index=0; index<numberOfItems;index++){ //1 based index
            String diskName = "disk_"+index;
            Disk disk = new Disk(diskName);
            //this.diskList.add(disk);
            this.diskTable.put(index,disk);
        }*/
    }

    //Singleton
    public static DiskManager getInstance(int numberOfItems) {
        if(diskManager == null){
            diskManager = new DiskManager(numberOfItems);
        }
        return diskManager;
    }

    //get disk from disk table (hash table) given disk index
    /*public Disk getDisk(int diskIndex){
        return this.diskTable.get(diskIndex);
    }*/

    //save file info into directory manager (hash table)
    public synchronized void saveFile(StringBuffer fileName, FileInfo fileInfo) {
        this.directoryManager.enter(fileName, fileInfo);
    }

    //get file info from directory manager (hash table)
    public synchronized FileInfo getFileInfo(StringBuffer fileName) {
        FileInfo fileInfo = this.directoryManager.lookup(fileName);
        return fileInfo;
    }




}
