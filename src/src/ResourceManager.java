package src;

/*
    A ResourceManager is responsible for allocating a resource like a Disk or a Printer to a specific Thread.
    Note you will have two instances: one for allocating Disks and another for allocating Printers,
    but I suggest you use inheritance to specialize for Disks and Printers.
    The implementation will be something like the following (you may use mine,
    but be sure you understand what it does and how it works;
    do you know why these methods must be synchronized?):
 */
public class ResourceManager {
    boolean isFree[];
    ResourceManager(int numberOfItems) {
        isFree = new boolean[numberOfItems];
        for (int i=0; i<isFree.length; ++i)
            isFree[i] = true;                   //set all elements to true
    }
    synchronized int request() {
        while (true) {
            for (int i = 0; i < isFree.length; ++i) {
                if ( isFree[i] ) {
                    isFree[i] = false;
                    //System.out.println("Debug: ResourceManager free resource #"+i);
                    return i;
                }
            }
            try {
                this.wait(); // block until someone releases Resource
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    synchronized void release( int index ) {
        //System.out.println("Debug: ResourceManager released resource #"+index);
        isFree[index] = true;
        this.notify(); // let a blocked thread run
    }


    //get specific resource
    /*synchronized  int request(int resourceID){
        while(true){
            for(int i=0; i< isFree.length; ++i) {
                if(i==resourceID) {
                    if(isFree[resourceID]) {
                        isFree[resourceID] = false;
                        return i;
                    }
                }
            }
            try {
                this.wait(); // block until someone releases Resource
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }*/
}
