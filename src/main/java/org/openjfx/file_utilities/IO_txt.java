package org.openjfx.file_utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;

public class IO_txt implements FileWriters, FileReaders {

    @Override
    public <T> void write(ArrayList<T> data, String filepath) {
        try {
            Formatter x = new Formatter(filepath);
            for(T line : data){
                x.format("%s",line);
            }
            x.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> read(String filepath) {
        ArrayList<T> data = new ArrayList<>();
        String lines;
        try {
            File file = new File(filepath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while((lines = bufferedReader.readLine()) != null) {
                data.add((T)lines);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return data;
    }
}
