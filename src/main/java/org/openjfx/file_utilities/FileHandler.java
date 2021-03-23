package org.openjfx.file_utilities;

import java.util.ArrayList;

public class FileHandler<T> {

    private FileHandlerThreads<T> threadHandlers;
    protected final String SAVE_THREAD = "Save Thread";
    protected final String OPEN_THREAD = "Open Thread";

    @SuppressWarnings("unchecked")
    public void save(ArrayList<T> dataToSave, String filename, String msg){
        threadHandlers = FileHandlerThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileInfo<T> fileInfo = new FileInfo<>(filename, dataToSave, SAVE_THREAD, msg);
            threadHandlers.addToWaitingThreads(fileInfo);
        } else {
            threadHandlers.runSaveThread(dataToSave, filename, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void open(String filename, String msg){
        threadHandlers = FileHandlerThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileInfo<T> fileInfo = new FileInfo<>(filename, null, OPEN_THREAD, msg);
            threadHandlers.addToWaitingThreads(fileInfo);
        } else {
            threadHandlers.runOpenThread(filename, msg);
        }
    }
}
