package org.openjfx.fileUtilities.fileIO;

import org.openjfx.fileUtilities.FileReaders;
import org.openjfx.fileUtilities.FileWriters;
import java.io.*;
import java.util.ArrayList;

public class IO_bin implements FileWriters, FileReaders {

    @Override
    public <T> void write(ArrayList<T> toSave, File file) {
        try {
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            for(T data : toSave) os.writeObject(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings({"InfiniteLoopStatement", "unchecked"})
    public <T> ArrayList<T> read(File file) {
        ArrayList<T> data = new ArrayList<>();
        Object object;
        try {
            FileInputStream fs = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fs);
            while(true) {
                object = is.readObject();
                data.add((T) object);
            }
        } catch (IOException ignored){
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
