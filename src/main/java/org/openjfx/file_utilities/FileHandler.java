package org.openjfx.file_utilities;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.openjfx.gui_utilities.Dialogs;
import java.util.ArrayList;

public class FileHandler<T> {

    private final Writer<T> writer = new Writer<>();        // task
    private final Reader<T> reader = new Reader<>();        // task
    private boolean threadIsRunning = false;                // makes sure that save and open thread does not run at the same time
    private boolean threadIsWaiting = false;                // makes sure that save and open thread does not run at the same time
    private ArrayList<T> dataToSave;                        // used for backups
    private ArrayList<T> dataOpened;                        // used for backups
    private String fileToOpen;                              // used for backups
    private String fileToSave;                              // used for backups
    private String filepath = "src/main/java/database/";    // default path where files are saved and opened from
    private Alert loadingAlert;                             // loading alert that shows thread progress

    public void save(ArrayList<T> toSave, String filename, String msg){
        fileToSave = filepath + filename;
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
        fileToOpen = filepath + filename;

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
        writer.setOnScheduled((e) -> saveScheduled());
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
        reader.setOnScheduled((e) -> openScheduled());
        reader.setOnSucceeded((e) -> openSuccessful());
        reader.setOnRunning((e) -> loadingAlert.show());
        reader.setOnFailed(this::openFailed);

        Thread thread = new Thread(reader, "Open Thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void saveScheduled(){
        System.out.println("Save Thread is now running...");
    }

    private void openScheduled(){
        System.out.println("Open Thread is now running...");
    }

    private void saveSuccessful(){
        loadingAlert.close();
        System.out.println("Save Thread Successful!");
        threadIsRunning = false;

        if(threadIsWaiting) {
            open(fileToOpen, "Opening file...");
            threadIsWaiting = false;
        }
    }

    private void openSuccessful(){
        loadingAlert.close();
        System.out.println("Open Thread Successful!");
        System.out.println(reader.getValue());
        dataOpened = reader.getValue();
        threadIsRunning = false;

        if(threadIsWaiting) {
            save(dataToSave, fileToSave, "Saving file...");
            threadIsWaiting = false;
        }
    }

    private void saveFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadIsRunning = false;
        System.out.println("Save Thread Failed!");
        System.err.println(e.getSource().getException().getMessage());
    }

    private void openFailed(WorkerStateEvent e){
        loadingAlert.close();
        threadIsRunning = false;
        System.out.println("Open Thread Failed!");
        System.err.println(e.getSource().getException().getMessage());
    }
}
