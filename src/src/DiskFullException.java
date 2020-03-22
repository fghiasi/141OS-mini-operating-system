package src;

/*
    custom exception for when a disk is full
 */
public class DiskFullException extends Exception {
    public DiskFullException(String message){
        super(message);
    }
}
