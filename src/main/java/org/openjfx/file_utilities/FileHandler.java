package org.openjfx.file_utilities;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.gui_utilities.Dialogs;
import java.util.ArrayList;

public class FileHandler<T> {

    private final String databasePath = "src/main/java/database/";
    private final Writer<T> writer = new Writer<>();
    private final Reader<T> reader = new Reader<>();
    private boolean threadIsRunning = false;
    private boolean threadIsWaiting = false;
    private String preloadedOpenFilename;                       // used for backup when a thread fails
    private String preloadedSaveFilename;                       // used for backup when a thread fails
    private ArrayList<T> dataToSave;                            // used for backup when a thread fails
    private Alert loadingAlert;

    public void save(ArrayList<T> toSave, String filename, String msg){
        String fileToSave = databasePath + filename;
        preloadedSaveFilename = filename;
        dataToSave = toSave;

        if(threadIsRunning) {
            threadIsWaiting = true;
            System.out.println("Save Thread is waiting...");
        } else {
            threadIsRunning = true;
            runSaveThread(toSave, fileToSave, msg);
        }
    }

    public void open(String filename, String msg){
        String fileToOpen = databasePath + filename;
        preloadedOpenFilename = filename;

        if(threadIsRunning) {
            threadIsWaiting = true;
            System.out.println("Open Thread is waiting...");
        } else {
            threadIsRunning = true;
            runOpenThread(fileToOpen, msg);
        }
    }

    private void runSaveThread(ArrayList<T> toSave, String filepath, String loadingMessage){
        loadingAlert = Dialogs.showLoadingDialog(writer, loadingMessage);

        writer.setData(toSave);
        writer.setFilepath(filepath);
        writer.setFileWriter(new IO_txt());
        writer.setOnScheduled((e) -> System.out.println("Save Thread is now running..."));
        writer.setOnSucceeded((e) -> saveSuccessful());
        writer.setOnRunning((e) -> loadingAlert.show());
        writer.setOnFailed(this::saveFailed);

        Thread thread = new Thread(writer, "Save Thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void runOpenThread(String filepath, String loadingMessage){
        loadingAlert = Dialogs.showLoadingDialog(reader, loadingMessage);

        reader.setFilepath(filepath);
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
            save(dataToSave, preloadedSaveFilename, "Saving a file...");
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
            save(dataToSave, preloadedSaveFilename, "Saving a file...");
            threadIsWaiting = false;
        }
    }
}
