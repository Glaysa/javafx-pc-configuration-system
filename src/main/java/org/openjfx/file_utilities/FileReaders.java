package org.openjfx.file_utilities;

import java.util.ArrayList;

public interface FileReaders {
    <T> ArrayList<T> read(String filepath);
}
