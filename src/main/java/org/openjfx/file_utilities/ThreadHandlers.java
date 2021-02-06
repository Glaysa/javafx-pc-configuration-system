package org.openjfx.file_utilities;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.gui_utilities.Dialogs;
import java.util.ArrayList;

public class ThreadHandlers<T> extends FileHandler<T> {

    private final String databasePath = "src/main/java/database/";
    private final Writer<T> writer = new Writer<>();
    private final Reader<T> reader = new Reader<>();
    private boolean threadIsRunning = false;
    private boolean threadIsWaiting = false;
    private String preloadedOpenFilename;                       // used for backup
    private String preloadedSaveFilename;                       // used for backup
    private ArrayList<T> preloadedData;                         // used for backup
    private Alert loadingAlert;

    void runSaveThread(ArrayList<T> toSave, String filename, String loadingMessage){
        loadingAlert = Dialogs.showLoadingDialog(writer, loadingMessage);
        preloadedSaveFilename = filename;
        preloadedData = toSave;

        writer.setData(toSave);
        writer.setFilepath(databasePath + filename);
        writer.setFileWriter(new IO_txt());
        writer.setOnScheduled((e) -> System.out.println("Save Thread is now running..."));
        writer.setOnSucceeded((e) -> saveSuccessful());
        writer.setOnRunning((e) -> loadingAlert.show());
        writer.setOnFailed(this::saveFailed);

        Thread thread = new Thread(writer, "Save Thread");
        thread.setDaemon(true);
        thread.start();
    }

    void runOpenThread(String filename, String loadingMessage){
        loadingAlert = Dialogs.showLoadingDialog(reader, loadingMessage);
        preloadedOpenFilename = filename;

        reader.setFilepath(databasePath + filename);
        reader.setFileReader(new IO_txt());
        reader.setOnScheduled((e) -> System.out.println("Open Thread is now running..."));
        reader.setOnSucceeded((e) -> openSuccessful());
        reader.setOnRunning((e) -> loadingAlert.show());
        reader.setOnFailed(this::openFailed);

        Thread thread = new Thread(reader, "Open Thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void saveSuccessful(){
        loadingAlert.close();
        System.out.println("Save Thread Successful!");
        threadIsRunning = false;

        if(threadIsWaiting) {
            open(preloadedOpenFilename, "Opening a file...");
            threadIsWaiting = false;
        }
    }

    private void openSuccessful(){
        loadingAlert.close();
        System.out.println("Open Thread Successful!");
        System.out.println(reader.getValue());
        threadIsRunning = false;

        if(threadIsWaiting) {
            save(preloadedData, preloadedSaveFilename, "Saving a file...");
            threadIsWaiting = false;
        }
    }

    private void saveFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadIsRunning = false;
        System.out.println("Save Thread Failed!");
        System.err.println(e.getSource().getException().getMessage());
        Dialogs.showWarningDialog(e.getSource().getException().getMessage());

        if(threadIsWaiting) {
            open(preloadedOpenFilename, "Opening a file...");
            threadIsWaiting = false;
        }
    }

    private void openFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadIsRunning = false;
        System.out.println("Open Thread Failed!");
        System.err.println(e.getSource().getException().getMessage());
        Dialogs.showWarningDialog(e.getSource().getException().getMessage());

        if(threadIsWaiting) {
            save(preloadedData, preloadedSaveFilename, "Saving a file...");
            threadIsWaiting = false;
        }
    }

    public void setThreadIsRunning(boolean threadIsRunning) {
        this.threadIsRunning = threadIsRunning;
    }

    public void setThreadIsWaiting(boolean threadIsWaiting) {
        this.threadIsWaiting = threadIsWaiting;
    }

    public boolean isThreadIsRunning() {
        return threadIsRunning;
    }

    /** File handler class can only use a single instance of this class */

    private static ThreadHandlers INSTANCE;

    public static ThreadHandlers getInstance() {
        if(INSTANCE == null) INSTANCE = new ThreadHandlers<>();
        return INSTANCE;
    }

    public ThreadHandlers() { }
}
