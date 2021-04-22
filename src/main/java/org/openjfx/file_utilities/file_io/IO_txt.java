package org.openjfx.file_utilities.file_io;

import org.openjfx.file_utilities.FileReaders;
import org.openjfx.file_utilities.FileWriters;
import org.openjfx.file_utilities.FileParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;

public class IO_txt implements FileWriters, FileReaders {

    @Override
    public <T> void write(ArrayList<T> data, File file) {
        try {
            Formatter x = new Formatter(file);
            for(T line : data){
                x.format("%s",line.toString());
            }
            x.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nSystem error: IO_txt.write()");
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public <T> ArrayList<T> read(File file) {
        ArrayList<T> data = new ArrayList<>();
        String lines;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while((lines = bufferedReader.readLine()) != null) {
                T obj = FileParser.setParser(lines);
                data.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nSystem error at: IO_txt.read()");
            throw new IllegalArgumentException(e.getMessage());
        }
        return data;
    }
}
