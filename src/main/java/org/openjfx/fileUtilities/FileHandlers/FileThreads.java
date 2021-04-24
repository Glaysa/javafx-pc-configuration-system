package org.openjfx.fileUtilities.FileHandlers;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.fileUtilities.FileParser;
import org.openjfx.fileUtilities.fileIO.IO_bin;
import org.openjfx.fileUtilities.fileIO.IO_txt;
import org.openjfx.fileUtilities.fileTasks.Reader;
import org.openjfx.fileUtilities.fileTasks.Writer;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.Indicators;
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
    private boolean threadRunning = false;
    private Alert loadingAlert;
    private File lastOpenedFile, currentOpenedFile;
    private Writer<T> writer = new Writer<>();
    private Reader<T> reader = new Reader<>();
    private final Queue<FileThreadInfo<T>> waitingThreads = new ArrayDeque<>();

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
            } else if(fileExtension.equals(".bin") || fileExtension.equals(".obj")) {
                writer.setFileWriter(new IO_bin());
            } else {
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
                currentOpenedFile = file;
            } else if(fileExtension.equals(".bin") || fileExtension.equals(".obj")) {
                reader.setFileReader(new IO_bin());
                currentOpenedFile = file;
            } else {
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
            AlertDialog.showWarningDialog(e.getMessage(),"Please try again!");
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
        Indicators.updateFileStatus(false);
        writer = new Writer<>();
        runWaitingThreads();
    }

    private void openSuccessful(){
        loadingAlert.close();
        threadRunning = false;
        processData(reader.getValue());
        System.out.println("Open Thread Successful!\n");
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
        System.err.println("Open Thread Failed: An error occurred!");
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
            } else if(fileThread.equals(OPEN_THREAD)){
                reader = new Reader<>();
                open(file, message);
            } else {
                System.out.println("No Available Threads");
            }
            waitingThreads.poll();
        }
    }

    /** Opening a file returns data, that data is processed here. */
    private void processData(ArrayList<T> data) {
        reader = new Reader<>();
        Object object = FileParser.convertToObject(data.get(0).toString());
        if(object instanceof PCComponents) {
            ComponentsCollection.clearCollection();
            for(T datum : data){
                Object p = FileParser.convertToObject(datum.toString());
                ComponentsCollection.addToCollection((PCComponents) p);
                lastOpenedFile = currentOpenedFile;
            }
            Indicators.updateFilename(lastOpenedFile.getName());
            Indicators.updateFileStatus(false);
        } else {
            System.out.println("Unable to load data");
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
