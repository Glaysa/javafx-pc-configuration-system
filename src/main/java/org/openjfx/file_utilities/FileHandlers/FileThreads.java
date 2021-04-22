package org.openjfx.file_utilities.FileHandlers;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.file_utilities.file_io.IO_bin;
import org.openjfx.file_utilities.file_io.IO_txt;
import org.openjfx.file_utilities.file_tasks.Reader;
import org.openjfx.file_utilities.file_tasks.Writer;
import org.openjfx.gui_utilities.AlertDialog;
import java.io.File;
import java.util.*;

/** Thread Operations:
 * - coded in a way where save thread and open thread does not run at the same time because they both have
 *   to show their progress dialog one at a time.
 * - with the help of booleans threadRunning and threadWaiting, we can tell which thread is running and waiting.
 * - when a thread is waiting, it will run after the previous thread is finished.
 * - thread.join() cannot be called because it freezes the UI. It's going to wait for the main thread to finish
 *   doing it's task, but the main thread is only finished doing it's task when we quit the program, thus
 *   thread.join() will wait until we close the program and causes the UI to freeze. */

class FileThreads<T> extends FileActions<T> {

    private final File defaultFile = new File("src/main/java/database/initialComponents.txt");
    private Writer<T> writer = new Writer<>();                          // task that runs on save thread
    private Reader<T> reader = new Reader<>();                          // task that runs on open thread
    private boolean threadRunning = false;                              // tells if a thread is currently running
    private Alert loadingAlert;                                         // Progress alert popup dialog
    private final Queue<FileThreadInfo<T>> waitingThreads = new ArrayDeque<>();   // tells if a thread is waiting to be run
    private File lastOpenedFile;                                   // used for saving changes

    /** FileActions class can only use a single instance of FileThreads (Singleton Pattern Implemented) */

    private static FileThreads INSTANCE;
    private FileThreads() { }
    public static FileThreads getInstance() {
        if(INSTANCE == null) INSTANCE = new FileThreads<>();
        return INSTANCE;
    }

    /** The following methods are responsible for assigning the correct file readers and writers depending on the file
        extension. */

    private void assignWriters(File file){
        String filename = file.getName();
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        try {
            if(fileExtension.equals(".txt")) {
                writer.setFileWriter(new IO_txt());
            }
            else if(fileExtension.equals(".bin")) {
                writer.setFileWriter(new IO_bin());
            }
            else {
                throw new IllegalArgumentException("Invalid File.\nSave only *.txt, *.bin");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void assignReaders(File file){
        String filename = file.getName();
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        try {
            if(fileExtension.equals(".txt")) {
                reader.setFileReader(new IO_txt());
                lastOpenedFile = file;
            }
            else if(fileExtension.equals(".bin")) {
                reader.setFileReader(new IO_bin());
                lastOpenedFile = file;
            }
            else {
                // currentOpenedFile = lastOpenedFile;
                throw new IllegalArgumentException("Invalid File.\nOpen only *.txt, *.bin");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** These methods are responsible for running the Writer and Reader tasks on a thread
     * and are also responsible for showing their progress dialog. */

    protected void runSaveThread(ArrayList<T> data, File file, String loadingMessage){
        try {
            loadingAlert = AlertDialog.showLoadingDialog(writer, loadingMessage);
            assignWriters(file);

            writer.setData(data);
            writer.setFilepath(file);
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
            AlertDialog.showWarningDialog(e.getMessage(),"");
            waitingThreads.poll();
            runWaitingThreads();
        }
    }

    protected void runOpenThread(File file, String loadingMessage){
        try {
            loadingAlert = AlertDialog.showLoadingDialog(reader, loadingMessage);
            assignReaders(file);

            reader.setFilepath(file);
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
            AlertDialog.showWarningDialog(e.getMessage(), "Please try again!");
            waitingThreads.poll();
            runWaitingThreads();
        }
    }

    /** When save or open thread is a success, they run all waiting threads
     * and open thread processes the data it opened.*/

    private void saveSuccessful(){
        loadingAlert.close();
        threadRunning = false;
        System.out.println("Save Thread Successful!\n");
        writer = new Writer<>();
        runWaitingThreads();
    }

    private void openSuccessful(){
        loadingAlert.close();
        threadRunning = false;
        processData(reader.getValue());
        System.out.println("Open Thread Successful!\n");
        reader = new Reader<>();
        runWaitingThreads();
    }

    /** When save or open thread fails, this method shows error messages
     * to user and developers and runs all waiting threads */

    private void saveFailed(WorkerStateEvent e){
        writer = new Writer<>();
        loadingAlert.close();
        threadRunning = false;

        // Error shown to the user
        String errorMessage = e.getSource().getException().getMessage();
        AlertDialog.showWarningDialog("System error - Failed to save file", errorMessage);
        // Run the next waiting threads if there are any
        runWaitingThreads();
        // Error shown to developers
        System.err.println("Save Thread Failed: An error occurred!");
        e.getSource().getException().printStackTrace();
        System.out.println();
    }

    private void openFailed(WorkerStateEvent e){
        reader = new Reader<>();
        loadingAlert.close();
        threadRunning = false;

        // Error shown to the user
        String errorMessage = e.getSource().getException().getMessage();
        AlertDialog.showWarningDialog("System error - Failed to open file", errorMessage);
        // Run the next waiting threads if there are any
        runWaitingThreads();
        // Error shown to developers
        System.err.println("Open Thread Failed: The default system data will be opened instead.");
        e.getSource().getException().printStackTrace();
        System.out.println();
    }

    /** This method runs all threads in the waiting threads Queue. */
    private void runWaitingThreads(){
        for(int i = 0; i < waitingThreads.size(); i++){

            String fileThread = waitingThreads.element().getFileThread();
            File file = waitingThreads.element().getFilename();
            ArrayList<T> fileData = waitingThreads.element().getFileData();
            String message = waitingThreads.element().getFileMsg();

            if(fileThread.equals(SAVE_THREAD)){
                writer = new Writer<>();
                save(fileData, file, message);
            }
            else if(fileThread.equals(OPEN_THREAD)){
                reader = new Reader<>();
                open(file, message);
            }
            else {
                System.out.println("No Available Threads");
            }
            waitingThreads.poll();
        }
    }

    /** Opening a file returns data, that data is processed here. */
    private void processData(ArrayList<T> data) {

        // Checks which object instance is the data
        T datum = data.get(0);
        String[] attributesLength = datum.toString().split(";");

        if(attributesLength.length == 5){

            // Clears the tableview first before loading mew data
            if(!data.isEmpty()) ComponentsCollection.clearCollection();
            // Load the new data to tableview
            for(T d : data) ComponentsCollection.addToCollection((PCComponents) d);
            // When opening a new file, it is by default not modified
            ComponentsCollection.setModified(false);

        } else {

            // Show a warning alert that the file is corrupted
            System.err.println("File is corrupted: Data not loaded");
            AlertDialog.showWarningDialog("File is corrupted!",
             "File data must either contain pc components or pc configurations.");

            // When the file that is opened is corrupted, load the default component list
            if(data.isEmpty()) {
                reader = new Reader<>();
                open(defaultFile,"Loading default data...");
            }
        }
    }

    /** Adds a thread to the waiting threads queue. */
    public void addToWaitingThreads(FileThreadInfo<T> threadWaiting) {
        waitingThreads.add(threadWaiting);
    }

    /** Tells if a thread is running. */
    public boolean isThreadRunning() {
        return threadRunning;
    }

    /** Used to keep the current opened file for unsaved changes. */
    protected File getCurrentOpenedFile(){
        return lastOpenedFile;
    }
}
