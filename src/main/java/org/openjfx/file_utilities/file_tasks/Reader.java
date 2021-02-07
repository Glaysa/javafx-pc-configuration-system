package org.openjfx.file_utilities.file_tasks;

import javafx.concurrent.Task;
import org.openjfx.file_utilities.FileReaders;
import java.util.ArrayList;

public class Reader<T> extends Task<ArrayList<T>> {

    private FileReaders fileReaders;
    private String filepath;

    private ArrayList<T> runFileReader(){
        return fileReaders.read(filepath);
    }

    public void setFileReader(FileReaders fileReaders) {
        this.fileReaders = fileReaders;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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
