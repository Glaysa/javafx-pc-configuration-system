package org.openjfx.file_utilities;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.custom_exceptions.UnsupportedFileReader;
import org.openjfx.custom_exceptions.UnsupportedFileWriter;
import org.openjfx.file_utilities.file_io.IO_bin;
import org.openjfx.file_utilities.file_io.IO_txt;
import org.openjfx.file_utilities.file_tasks.Reader;
import org.openjfx.file_utilities.file_tasks.Writer;
import org.openjfx.gui_utilities.Dialogs;

import java.util.*;

public class FileHandlerThreads<T> extends FileHandler<T> {

    private final String databasePath = "src/main/java/database/";      // where files are saved and opened
    private Writer<T> writer = new Writer<>();                          // task that runs on save thread
    private Reader<T> reader = new Reader<>();                          // task that runs on open thread
    private boolean threadRunning = false;                              // tells if a thread is currently running
    private String backupOpenFile;                                      // used for backup
    private String backupSaveFile;                                      // used for backup
    private ArrayList<T> backupSaveData;                                // used for backup
    private ArrayList<T> backupOpenData;                                // used for backup
    private Alert loadingAlert;                                         // Progress alert popup
    private final Queue<FileInfo<T>> waitingThreads = new ArrayDeque<>();    // tells if a thread is waiting to be run

    /** FileHandler class can only use a single instance of this class (Singleton Pattern Implemented) */

    private static FileHandlerThreads INSTANCE;
    public FileHandlerThreads() { }
    public static FileHandlerThreads getInstance() {
        if(INSTANCE == null) INSTANCE = new FileHandlerThreads<>();
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

    private void assignWriters(String filename){
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        switch (fileExtension) {
            case ".txt": writer.setFileWriter(new IO_txt()); break;
            case ".bin": writer.setFileWriter(new IO_bin()); break;
            default: throw new UnsupportedFileWriter("File not supported: Please save only *.txt, *.bin");
        }
    }

    private void assignReaders(String filename){
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        switch (fileExtension) {
            case ".txt": reader.setFileReader(new IO_txt()); break;
            case ".bin": reader.setFileReader(new IO_bin()); break;
            default: throw new UnsupportedFileReader("File not supported: Please open only *.txt, *.bin");
        }
    }

    protected void runSaveThread(ArrayList<T> data, String filename, String loadingMessage){
        try {
            loadingAlert = Dialogs.showLoadingDialog(writer, loadingMessage);
            assignWriters(filename);
            writer.setData(data);
            writer.setFilepath(databasePath + filename);
            writer.setOnScheduled((e) -> System.out.println("Save Thread is now running..."));
            writer.setOnSucceeded((e) -> saveSuccessful());
            writer.setOnRunning((e) -> loadingAlert.show());
            writer.setOnFailed(this::saveFailed);

            Thread thread = new Thread(writer, "Save Thread");
            thread.setDaemon(true);
            thread.start();
            threadRunning = true;

        } catch (IllegalArgumentException e) {
            threadRunning = false;
            Dialogs.showWarningDialog(e.getMessage(),"");

            // If prev thread did not run, remove it from the queue and run the next waiting thread
            waitingThreads.poll();
            runWaitingThreads();
        }
    }

    protected void runOpenThread(String filename, String loadingMessage){
        try {
            loadingAlert = Dialogs.showLoadingDialog(reader, loadingMessage);
            assignReaders(filename);
            reader.setFilepath(databasePath + filename);
            reader.setOnScheduled((e) -> System.out.println("Open Thread is now running..."));
            reader.setOnSucceeded((e) -> openSuccessful());
            reader.setOnRunning((e) -> loadingAlert.show());
            reader.setOnFailed(this::openFailed);

            Thread thread = new Thread(reader, "Open Thread");
            thread.setDaemon(true);
            thread.start();
            threadRunning = true;

        } catch (IllegalArgumentException e) {
            threadRunning = false;
            Dialogs.showWarningDialog(e.getMessage(), "");

            // If prev thread did not run, remove it from the queue and run the next waiting thread
            waitingThreads.poll();
            runWaitingThreads();
        }
    }

    private void saveSuccessful(){
        loadingAlert.close();
        threadRunning = false;
        System.out.println("Save Thread Successful!\n");
        runWaitingThreads();
    }

    private void openSuccessful(){
        loadingAlert.close();
        threadRunning = false;
        System.out.println("Open Thread Successful!\n");
        runWaitingThreads();
    }

    private void saveFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadRunning = false;

        // Error shown to the user
        String errorMessage = e.getSource().getException().getMessage();
        Dialogs.showWarningDialog("System error - Failed to save file", errorMessage);

        runWaitingThreads();

        // Error shown to developers
        System.err.println("\nSystem error: Save Thread Failed!\n");
        e.getSource().getException().printStackTrace();
    }

    private void openFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadRunning = false;

        // Error shown to the user
        String errorMessage = e.getSource().getException().getMessage();
        Dialogs.showWarningDialog("System error - Failed to open file", errorMessage);

        runWaitingThreads();

        // Error shown to developers
        System.err.println("\nSystem error: Open Thread Failed!\n");
        e.getSource().getException().printStackTrace();
    }

    private void runWaitingThreads(){
        for(int i = 0; i < waitingThreads.size(); i++){

            String fileThread = waitingThreads.element().getFileThread();
            String filename = waitingThreads.element().getFilename();
            ArrayList<T> fileData = waitingThreads.element().getFileData();

            if(fileThread.equals(SAVE_THREAD)){
                writer = new Writer<>();
                save(fileData, filename, "Saving a file...");
            }
            else if(fileThread.equals(OPEN_THREAD)){
                reader = new Reader<>();
                open(filename, "Opening a file...");
            }
            else {
                System.out.println("No Available Threads");
            }
            waitingThreads.poll();
        }
    }

    public void setBackupSaveFile(String backupSaveFile) {
        this.backupSaveFile = backupSaveFile;
    }

    public void setBackupOpenFile(String backupOpenFile) {
        this.backupOpenFile = backupOpenFile;
    }

    public void setBackupSaveData(ArrayList<T> backupSaveData) {
        this.backupSaveData = backupSaveData;
    }

    public void addToWaitingThreads(FileInfo<T> threadWaiting) {
        waitingThreads.add(threadWaiting);
    }

    public boolean isThreadRunning() {
        return threadRunning;
    }

    public ArrayList<T> getBackupOpenData(){
        return backupOpenData;
    }
}
