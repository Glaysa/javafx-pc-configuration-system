package org.openjfx.file_utilities;

import java.util.ArrayList;

public class FileHandler<T> {

    private ThreadHandlers<T> threadHandlers;

    @SuppressWarnings("unchecked")
    public void save(ArrayList<T> toSave, String filename, String msg){
        threadHandlers = ThreadHandlers.getInstance();
        if(threadHandlers.isThreadIsRunning()) {
            threadHandlers.setThreadIsWaiting(true);
            System.out.println("Save Thread is waiting...");
        } else {
            threadHandlers.setThreadIsRunning(true);
            threadHandlers.runSaveThread(toSave, filename, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void open(String filename, String msg){
        threadHandlers = ThreadHandlers.getInstance();
        if(threadHandlers.isThreadIsRunning()) {
            threadHandlers.setThreadIsWaiting(true);
            System.out.println("Open Thread is waiting...");
        } else {
            threadHandlers.setThreadIsRunning(true);
            threadHandlers.runOpenThread(filename, msg);
        }
    }
}
