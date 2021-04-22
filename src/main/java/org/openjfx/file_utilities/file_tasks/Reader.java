package org.openjfx.file_utilities.file_tasks;

import javafx.concurrent.Task;
import org.openjfx.file_utilities.FileReaders;
import java.io.File;
import java.util.ArrayList;

/** This class is responsible for executing the correct file readers found in file_io directory. */

public class Reader<T> extends Task<ArrayList<T>> {

    private FileReaders fileReaders;
    private File file;

    private ArrayList<T> runFileReader(){
        return fileReaders.read(file);
    }

    public void setFileReader(FileReaders fileReaders) {
        this.fileReaders = fileReaders;
    }

    public void setFilepath(File file) {
        this.file = file;
    }

    @Override
    protected ArrayList<T> call() throws Exception {
        for(int i = 0; i < 100; i++){
            Thread.sleep(20);
            updateProgress(i, 100);
        }
        return runFileReader();
    }
}
