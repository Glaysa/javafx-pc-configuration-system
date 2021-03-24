package org.openjfx.file_utilities.FileHandlers;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.file_utilities.file_io.IO_bin;
import org.openjfx.file_utilities.file_io.IO_txt;
import org.openjfx.file_utilities.file_tasks.Reader;
import org.openjfx.file_utilities.file_tasks.Writer;
import org.openjfx.gui_utilities.Dialogs;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class FileThreads<T> extends FileActions<T> {

    private final String databasePath = "src/main/java/database/";      // where files are saved and opened
    private Writer<T> writer = new Writer<>();                          // task that runs on save thread
    private Reader<T> reader = new Reader<>();                          // task that runs on open thread
    private boolean threadRunning = false;                              // tells if a thread is currently running
    private String backupOpenFile;                                      // used for backup
    private String backupSaveFile;                                      // used for backup
    private ArrayList<T> backupSaveData;                                // used for backup
    private ArrayList<T> backupOpenData;                                // used for backup
    private Alert loadingAlert;                                         // Progress alert popup dialog
    private final Queue<ThreadInfo<T>> waitingThreads = new ArrayDeque<>();    // tells if a thread is waiting to be run

    /** FileActions class can only use a single instance of FileThreads (Singleton Pattern Implemented) */

    private static FileThreads INSTANCE;
    private FileThreads() { }
    public static FileThreads getInstance() {
        if(INSTANCE == null) INSTANCE = new FileThreads<>();
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
        try {
            if(fileExtension.equals(".txt")) writer.setFileWriter(new IO_txt());
            else if(fileExtension.equals(".bin")) writer.setFileWriter(new IO_bin());
            else {Dialogs.showWarningDialog("Unsupported format", "Open only: *.txt, *.bin");}
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void assignReaders(String filename){
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        try {
            if(fileExtension.equals(".txt")) reader.setFileReader(new IO_txt());
            else if(fileExtension.equals(".bin")) reader.setFileReader(new IO_bin());
            else {Dialogs.showWarningDialog("Unsupported format", "Open only: *.txt, *.bin");}
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void fileExists(String filename) throws FileNotFoundException {
        File f = new File(databasePath + filename);
        if(!f.exists()) throw new FileNotFoundException(filename + " does not exist.");
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

            Thread thread = new Thread(writer, SAVE_THREAD);
            thread.setDaemon(true);
            thread.start();
            threadRunning = true;

        } catch (Exception e) {
            threadRunning = false;
            Dialogs.showWarningDialog(e.getMessage(),"");
            waitingThreads.poll();
            runWaitingThreads();
        }
    }

    protected void runOpenThread(String filename, String loadingMessage){
        try {
            loadingAlert = Dialogs.showLoadingDialog(reader, loadingMessage);
            fileExists(filename);
            assignReaders(filename);

            reader.setFilepath(databasePath + filename);
            reader.setOnScheduled((e) -> System.out.println("Open Thread is now running..."));
            reader.setOnSucceeded((e) -> openSuccessful());
            reader.setOnRunning((e) -> loadingAlert.show());
            reader.setOnFailed(this::openFailed);

            Thread thread = new Thread(reader, OPEN_THREAD);
            thread.setDaemon(true);
            thread.start();
            threadRunning = true;

        } catch (Exception e) {
            threadRunning = false;
            Dialogs.showWarningDialog(e.getMessage(), "");
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
        processData(reader.getValue());
        System.out.println("Open Thread Successful!\n");
        runWaitingThreads();
    }

    private void saveFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadRunning = false;

        // Error shown to the user
        String errorMessage = e.getSource().getException().getMessage();
        Dialogs.showWarningDialog("System error - Failed to save file", errorMessage);

        // Run the next waiting threads if there are any
        runWaitingThreads();

        // Error shown to developers
        System.err.println("Save Thread Failed: An error occurred!");
        e.getSource().getException().printStackTrace();
        System.out.println();
    }

    private void openFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadRunning = false;

        // Error shown to the user
        String errorMessage = e.getSource().getException().getMessage();
        Dialogs.showWarningDialog("System error - Failed to open file", errorMessage);

        // Run the next waiting threads if there are any
        runWaitingThreads();

        // Error shown to developers
        System.err.println("Open Thread Failed: The default system data will be opened instead.");
        e.getSource().getException().printStackTrace();
        System.out.println();
    }

    private void runWaitingThreads(){
        for(int i = 0; i < waitingThreads.size(); i++){

            String fileThread = waitingThreads.element().getFileThread();
            String filename = waitingThreads.element().getFilename();
            ArrayList<T> fileData = waitingThreads.element().getFileData();
            String fileMsg = waitingThreads.element().getFileMsg();

            if(fileThread.equals(SAVE_THREAD)){
                writer = new Writer<>();
                save(fileData, filename, fileMsg);
            }
            else if(fileThread.equals(OPEN_THREAD)){
                reader = new Reader<>();
                open(filename, fileMsg);
            }
            else {
                System.out.println("No Available Threads");
            }
            waitingThreads.poll();
        }
    }

    /** Opening a file returns data, these data are processed here */
    private void processData(ArrayList<T> data) {
        T datum = data.get(0);
        String[] attributesLength = datum.toString().split(";");

        if(attributesLength.length == 5){
            if(!data.isEmpty()) ComponentsCollection.clearCollection();
            System.out.println("File data is an instance of PCComponents");
            for(T d : data) ComponentsCollection.addToCollection((PCComponents) d);
        } else {
            System.err.println("File is corrupted: Data not loaded");
            Dialogs.showWarningDialog(
                    "File is corrupted!",
                    "File data must either contain pc components or pc configurations.");

            if(data.isEmpty()) {
                reader = new Reader<>();
                open("initialComponents.txt","Loading default data...");
            }
        }
    }

    public void addToWaitingThreads(ThreadInfo<T> threadWaiting) {
        waitingThreads.add(threadWaiting);
    }

    public boolean isThreadRunning() {
        return threadRunning;
    }
}
