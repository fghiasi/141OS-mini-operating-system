//package com.company;
package src;
import java.util.Hashtable;

/*
    The DirectoryManager is a table that knows where files are stored on disk via mapping file names into disk sectors.
    You should use the pre-defined Hashtable to store this information (in the form of FileInfo),
    but it must be private to class DirectoryManager.
 */
public class DirectoryManager {
    //private Hashtable<String, FileInfo> T = new Hashtable<String, FileInfo>();
    private Hashtable<String, FileInfo> T;
    DirectoryManager(){
        T = new Hashtable<String, FileInfo>();
    }

    void enter(StringBuffer key, FileInfo file) {
        T.put(key.toString(), file);
    }

    FileInfo lookup(StringBuffer key) {
        FileInfo fileInfo = T.get(key.toString());
        if(fileInfo!=null){
            return fileInfo;
        } else {
            return null;
        }
    }

}
