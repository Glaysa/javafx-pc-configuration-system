package org.openjfx.fileUtilities.FileHandlers;

import org.openjfx.fileUtilities.fileTasks.Reader;
import org.openjfx.fileUtilities.fileTasks.Writer;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/** This class is responsible for calling the open and save threads. They are often called in the controllers. */

public class FileActions<T> {

    private FileThreads<T> threadHandlers;
    protected final String SAVE_THREAD = "Save Thread";
    protected final String OPEN_THREAD = "Open Thread";
    protected Writer<T> writer = new Writer<>();
    protected Reader<T> reader = new Reader<>();
    protected final Queue<FileThreadInfo<T>> waitingThreads = new ArrayDeque<>();

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
}
