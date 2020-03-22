package src;

/*
    A FileInfo object will hold the disk number,
    length of the file (in sectors), and the index of the starting sector
    (i.e. which sector the first line of the file is in).
    This FileInfo will be used in the DirectoryManager to hold meta information about a file
    during saving as well as to inform a PrintJobThread when printing.
 */
public class FileInfo {

    private int diskNumber;
    private int startingSector;
    private int fileLength;             //in sectors

    FileInfo(int diskNumber,
             int startingSector,
             int fileLength){
        this.diskNumber = diskNumber;
        this.startingSector = startingSector;
        this.fileLength = fileLength;
    }

    public int getDiskNumber() {
        return diskNumber;
    }

    public void setDiskNumber(int diskNumber) {
        this.diskNumber = diskNumber;
    }

    public int getStartingSector() {
        return startingSector;
    }

    public void setStartingSector(int startingSector) {
        this.startingSector = startingSector;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }
}
