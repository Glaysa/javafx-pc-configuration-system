package org.openjfx.fileUtilities.FileHandlers;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
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
 *
 * - coded in a way where save thread and open thread does not run at the same time because they both have
 *   to show their progress dialog one at a time.
 *
 * - with the help of booleans threadRunning and threadWaiting, we can tell which thread is running and waiting.
 * - when a thread is waiting, it will run after the previous thread is finished. This is achieved by storing the
 *   waiting thread in a queue.
 *
 * - thread.join() cannot be called because it freezes the UI. It's going to wait for the main thread to finish
 *   doing it's task, but the main thread is only finished doing it's task when we quit the program, thus
 *   other threads will not be executed until the program stops, which basically means, nothing will run.
 *
 * - The same thread cannot run twice, therefore when opening or saving a file, the reader task and writer task
 *   must always have a new instantiation. They get a new instance whether the save or open threads
 *   are successful or not. */

class FileThreads extends FileActions {

    private boolean threadRunning = false;
    private Alert loadingAlert;
    private File lastOpenedFile, currentOpenedFile;
    private Writer<Object> writer = new Writer<>();
    private Reader<Object> reader = new Reader<>();
    private final Queue<FileThreadInfo<Object>> waitingThreads = new ArrayDeque<>();
    private File defaultSystemData;

    /** FileActions class can only use a single instance of FileThreads (Singleton Pattern Implemented) */

    private static FileThreads INSTANCE;
    private FileThreads() { }
    public static FileThreads getInstance() {
        if(INSTANCE == null) INSTANCE = new FileThreads();
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
                throw new IllegalArgumentException("Invalid File extension.\nSave only *.txt, *.bin");
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
                throw new IllegalArgumentException("Invalid File extension.\nOpen only *.txt, *.bin");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** These methods are responsible for running the Writer and Reader tasks on a thread
     * and are also responsible for showing their progress dialog. */

    protected void runSaveThread(ArrayList<Object> data, File file, String loadingMessage){
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

    /** When save or open thread is a success, all waiting threads will run one at a time
     * and open thread processes the data the file contains. */

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

    /** When save or open thread fails, this method will show error messages
     * to user and developers and runs all waiting threads one at a time. */

    private void saveFailed(WorkerStateEvent e){
        writer = new Writer<>();
        handleFailedThread(e);
    }

    private void openFailed(WorkerStateEvent e){
        reader = new Reader<>();
        handleFailedThread(e);
    }

    private void handleFailedThread(WorkerStateEvent e){
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
            ArrayList<Object> fileData = waitingThreads.element().getFileData();
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

    /* TODO: Fix issue, opens components in configuration window */

    /** Opening a file returns data, that data is processed here. */
    private void processData(ArrayList<Object> data) {
        try {
            reader = new Reader<>();

            // If opened file is empty
            if(data.isEmpty()) {
                AlertDialog.showWarningDialog("File is empty!","");
                return;
            }

            // Check object instance
            Object object = FileParser.convertToObject(data.get(0).toString());

            // If object instance is PC components
            if(object instanceof PCComponents) {
                defaultSystemData = new File("src/main/java/database/initialComponents.txt");
                ComponentsCollection.clearCollection();
                for(Object datum : data){
                    Object p = FileParser.convertToObject(datum.toString());
                    ComponentsCollection.addToCollection((PCComponents) p);
                    lastOpenedFile = currentOpenedFile;
                }
                ComponentsCollection.setModified(false);
                Indicators.updateFilename(lastOpenedFile.getName());
                Indicators.updateFileStatus(false);

            // If object instance is PC configurations
            } else if(object instanceof PCConfigurations){
                defaultSystemData = new File("src/main/java/database/initialConfiguration.txt");
                ConfigurationCollection.clearCollection();
                for(Object datum : data){
                    Object p = FileParser.convertToObject(datum.toString());
                    ConfigurationCollection.addConfiguration((PCConfigurations) p);
                    lastOpenedFile = currentOpenedFile;
                }
            }
        } catch (IllegalArgumentException e) {
            AlertDialog.showWarningDialog(e.getMessage(),"The default system data will be loaded!");
            open(defaultSystemData, "Loading system data...");
        }
    }

    /** Adds a thread to the waiting threads queue. */
    public void addToWaitingThreads(FileThreadInfo<Object> threadWaiting) {
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
