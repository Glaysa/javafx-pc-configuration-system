package org.openjfx.fileUtilities.FileHandlers;

import java.io.File;
import java.util.ArrayList;

/** This class is responsible for calling the open and save threads. They are often called in the controllers. */

public class FileActions<T> {

    private FileThreads<T> threadHandlers;
    protected final String SAVE_THREAD = "Save Thread";
    protected final String OPEN_THREAD = "Open Thread";

    @SuppressWarnings("unchecked")
    public void save(ArrayList<T> dataToSave, File file, String msg){
        threadHandlers = FileThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileThreadInfo<T> fileThreadInfo = new FileThreadInfo<>(file, dataToSave, SAVE_THREAD, msg);
            threadHandlers.addToWaitingThreads(fileThreadInfo);
        } else {
            threadHandlers.runSaveThread(dataToSave, file, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void open(File file, String msg){
        threadHandlers = FileThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileThreadInfo<T> fileThreadInfo = new FileThreadInfo<>(file, null, OPEN_THREAD, msg);
            threadHandlers.addToWaitingThreads(fileThreadInfo);
        } else {
            threadHandlers.runOpenThread(file, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void saveChanges(ArrayList<T> dataToSave){
        threadHandlers = FileThreads.getInstance();
        File currentOpenedFile = threadHandlers.getCurrentOpenedFile();
        if(threadHandlers.isThreadRunning()) {
            FileThreadInfo<T> fileThreadInfo = new FileThreadInfo<>(currentOpenedFile, dataToSave, SAVE_THREAD, "Saving changes...");
            threadHandlers.addToWaitingThreads(fileThreadInfo);
        } else {
            threadHandlers.runSaveThread(dataToSave, currentOpenedFile, "Saving changes...");
        }
    }

    @SuppressWarnings("unchecked")
    public String getCurrentOpenedFilename(){
        threadHandlers = FileThreads.getInstance();
        return threadHandlers.getCurrentOpenedFile().getName();
    }
}
