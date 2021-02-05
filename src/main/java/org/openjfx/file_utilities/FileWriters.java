package org.openjfx.file_utilities;

import java.util.ArrayList;

public interface FileWriters {
    <T> void write(ArrayList<T> toSave, String filename);
}
