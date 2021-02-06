package org.openjfx.file_utilities;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.gui_utilities.Dialogs;
import java.util.ArrayList;

public class ThreadHandlers<T> extends FileHandler<T> {

    private final String databasePath = "src/main/java/database/";      // where files are saved and opened
    private final Writer<T> writer = new Writer<>();                    // task that runs on save thread
    private final Reader<T> reader = new Reader<>();                    // task that runs on open thread
    private boolean threadRunning = false;                              // tells if a thread is currently running
    private boolean threadWaiting = false;                              // tells if a thread is waiting to be run
    private String preloadedOpenFilename;                               // used for backup
    private String preloadedSaveFilename;                               // used for backup
    private ArrayList<T> preloadedData;                                 // used for backup
    private Alert loadingAlert;                                         // Progress alert popup

    /** FileHandler class can only use a single instance of this class (Singleton Pattern Implemented) */

    private static ThreadHandlers INSTANCE;
    public ThreadHandlers() { }
    public static ThreadHandlers getInstance() {
        if(INSTANCE == null) INSTANCE = new ThreadHandlers<>();
        return INSTANCE;
    }

    /** Thread Operations:
     * - coded in a way where save thread and open thread does not run at the same time because they both have
     *   to show their progress alert one at a time.
     * - with the help of booleans threadRunning and threadWaiting, we can tell which thread is running and waiting.
     * - when a thread is waiting, it will run after the first thread is finished.
     * - thread.join() cannot be called because it freezes the UI. It's going to wait for the main thread to finish
     *   doing it's task, but the main thread is only finished doing it's task when we quit the program, thus
     *   thread.join() will wait until we close the program and causes the UI to freeze. */

    protected void runSaveThread(ArrayList<T> toSave, String filename, String loadingMessage){
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

    protected void runOpenThread(String filename, String loadingMessage){
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
        threadRunning = false;

        if(threadWaiting) {
            open(preloadedOpenFilename, "Opening a file...");
            threadWaiting = false;
        }
    }

    private void openSuccessful(){
        loadingAlert.close();
        System.out.println("Open Thread Successful!");
        System.out.println(reader.getValue());
        threadRunning = false;

        if(threadWaiting) {
            save(preloadedData, preloadedSaveFilename, "Saving a file...");
            threadWaiting = false;
        }
    }

    private void saveFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadRunning = false;
        System.out.println("Save Thread Failed!");
        System.err.println(e.getSource().getException().getMessage());
        Dialogs.showWarningDialog(e.getSource().getException().getMessage());

        if(threadWaiting) {
            open(preloadedOpenFilename, "Opening a file...");
            threadWaiting = false;
        }
    }

    private void openFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadRunning = false;
        System.out.println("Open Thread Failed!");
        System.err.println(e.getSource().getException().getMessage());
        Dialogs.showWarningDialog(e.getSource().getException().getMessage());

        if(threadWaiting) {
            save(preloadedData, preloadedSaveFilename, "Saving a file...");
            threadWaiting = false;
        }
    }

    public void setThreadRunning(boolean threadRunning) {
        this.threadRunning = threadRunning;
    }

    public void setThreadWaiting(boolean threadWaiting) {
        this.threadWaiting = threadWaiting;
    }

    public boolean isThreadRunning() {
        return threadRunning;
    }
}
