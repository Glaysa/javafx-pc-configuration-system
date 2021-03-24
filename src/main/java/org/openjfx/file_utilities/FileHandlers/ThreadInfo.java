package org.openjfx.file_utilities.FileHandlers;

import java.util.ArrayList;

/** This class is used to save waiting threads in a queue. With this, we can access all info about the thread that is
    waiting and run the correct operations. The threads can either be a save thread operation or open thread operation.
    Save thread and Open thread cannot run at the same time because they have to show their progress dialog one at a time.
    If 10 open thread are called, they will run after one another, thread.join is not used because it freezes the UI.
 */

public class ThreadInfo<T> {

    private final String fileThread;
    private final String filename;
    private final ArrayList<T> fileData;
    private final String fileMsg;

    public ThreadInfo(String filename, ArrayList<T> fileData, String fileThread, String fileMsg){
        this.fileThread = fileThread;
        this.filename = filename;
        this.fileData = fileData;
        this.fileMsg = fileMsg;
    }

    public String getFileThread(){
        return fileThread;
    }

    public String getFilename(){
        return filename;
    }

    public ArrayList<T> getFileData(){
        return fileData;
    }

    public String getFileMsg(){
        return fileMsg;
    }
}
