package org.openjfx.file_utilities;

import java.util.ArrayList;

public class FileInfo<T> {
    private final String fileThread;
    private final String filename;
    private final ArrayList<T> fileData;

    public FileInfo(String filename, ArrayList<T> fileData, String fileThread){
        this.fileThread = fileThread;
        this.filename = filename;
        this.fileData = fileData;
    }

    public String getFileThread(){
        return fileThread;
    }

    public String getFilename(){
        return filename;
    }

    public ArrayList<T> getFileData(){
        return fileData;
    }
}
