package org.openjfx.fileUtilities.FileHandlers;

import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;

/** This class is responsible for calling the open and save threads */

public class FileActions {

    private FileThreads threadHandlers;
    protected final String SAVE_THREAD = "Save Thread";
    protected final String OPEN_THREAD = "Open Thread";

    public void save(ArrayList<Object> dataToSave, File file, String msg){
        threadHandlers = FileThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileThreadInfo<Object> fileThreadInfo = new FileThreadInfo<>(file, dataToSave, SAVE_THREAD, msg);
            threadHandlers.addToWaitingThreads( fileThreadInfo);
        } else {
            threadHandlers.runSaveThread(dataToSave, file, msg);
        }
    }

    public void open(File file, String msg){
        threadHandlers = FileThreads.getInstance();
        if(threadHandlers.isThreadRunning()) {
            FileThreadInfo<Object> fileThreadInfo = new FileThreadInfo<>(file, null, OPEN_THREAD, msg);
            threadHandlers.addToWaitingThreads(fileThreadInfo);
        } else {
            threadHandlers.runOpenThread(file, msg);
        }
    }

    public void saveChanges(ArrayList<Object> dataToSave, String msg){
        threadHandlers = FileThreads.getInstance();
        File currentOpenedFile = threadHandlers.getLastOpenedFile();
        save(dataToSave, currentOpenedFile, msg);
    }

    /** Initializes a file chooser */
    public FileChooser getFileChooser(){
        File initialDir = new File(System.getProperty("user.home") + "\\Desktop");
        FileChooser.ExtensionFilter f1 = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        FileChooser.ExtensionFilter f2 = new FileChooser.ExtensionFilter("Binary Files", "*.bin");
        FileChooser.ExtensionFilter f3 = new FileChooser.ExtensionFilter("Object Files", "*.obj");
        FileChooser.ExtensionFilter f4 = new FileChooser.ExtensionFilter("All Files", "*.*");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialDir);
        fileChooser.getExtensionFilters().addAll(f1, f2, f3, f4);
        return fileChooser;
    }
}
