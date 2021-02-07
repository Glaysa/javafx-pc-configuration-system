package org.openjfx.file_utilities.file_io;

import org.openjfx.file_utilities.FileReaders;
import org.openjfx.file_utilities.FileWriters;
import java.util.ArrayList;

public class IO_bin implements FileWriters, FileReaders {

    @Override
    public <T> ArrayList<T> read(String filepath) {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Override
    public <T> void write(ArrayList<T> toSave, String filename) {
        throw new UnsupportedOperationException("Not yet Implemented");
    }
}
