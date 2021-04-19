package org.openjfx.file_utilities.FileHandlers;

import java.util.ArrayList;

/** This class is responsible for calling the open and save threads. They are often called in the controllers. */

public class FileActions<T> {

    private FileThreads<T> threadHandlers;
    protected final String SAVE_THREAD = "Save Thread";
    protected final String OPEN_THREAD = "Open Thread";

    @SuppressWarnings("unchecked")
    public void save(ArrayList<T> dataToSave, String filename, String msg){
        threadHandlers = FileThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileThreadInfo<T> fileThreadInfo = new FileThreadInfo<>(filename, dataToSave, SAVE_THREAD, msg);
            threadHandlers.addToWaitingThreads(fileThreadInfo);
        } else {
            threadHandlers.runSaveThread(dataToSave, filename, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void open(String filename, String msg){
        threadHandlers = FileThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileThreadInfo<T> fileThreadInfo = new FileThreadInfo<>(filename, null, OPEN_THREAD, msg);
            threadHandlers.addToWaitingThreads(fileThreadInfo);
        } else {
            threadHandlers.runOpenThread(filename, msg);
        }
    }
}
