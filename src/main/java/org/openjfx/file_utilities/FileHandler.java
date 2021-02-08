package org.openjfx.file_utilities;

import java.util.ArrayList;

public class FileHandler<T> {

    private FileHandlerThreads<T> threadHandlers;

    @SuppressWarnings("unchecked")
    public void save(ArrayList<T> data, String filename, String msg){
        threadHandlers = FileHandlerThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            threadHandlers.setThreadWaiting(true);
            threadHandlers.setBackupSaveData(data);
            threadHandlers.setBackupSaveFile(filename);
            System.out.println("Save Thread is waiting...");
        } else {
            threadHandlers.runSaveThread(data, filename, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void open(String filename, String msg){
        threadHandlers = FileHandlerThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            threadHandlers.setThreadWaiting(true);
            threadHandlers.setBackupOpenFile(filename);
            System.out.println("Open Thread is waiting...");
        } else {
            threadHandlers.runOpenThread(filename, msg);
        }
    }

    public ArrayList<T> getBackupOpenData(){
        return threadHandlers.getBackupOpenData();
    }
}
