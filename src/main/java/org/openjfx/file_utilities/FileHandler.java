package org.openjfx.file_utilities;

import java.util.ArrayList;

public class FileHandler<T> {

    private FileHandlerThreads<T> threadHandlers;
    protected final String SAVE_THREAD = "Save Thread";
    protected final String OPEN_THREAD = "Open Thread";

    @SuppressWarnings("unchecked")
    public void save(ArrayList<T> data, String filename, String msg){
        threadHandlers = FileHandlerThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            threadHandlers.addToWaitingThreads(SAVE_THREAD);
            threadHandlers.setBackupSaveData(data);
            threadHandlers.setBackupSaveFile(filename);
        } else {
            threadHandlers.runSaveThread(data, filename, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void open(String filename, String msg){
        threadHandlers = FileHandlerThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            threadHandlers.addToWaitingThreads(OPEN_THREAD);
            threadHandlers.setBackupOpenFile(filename);
        } else {
            threadHandlers.runOpenThread(filename, msg);
        }
    }

    public ArrayList<T> getBackupOpenData(){
        return threadHandlers.getBackupOpenData();
    }
}
