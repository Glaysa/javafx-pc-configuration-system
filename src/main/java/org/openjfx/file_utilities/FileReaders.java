package org.openjfx.file_utilities;

import java.io.File;
import java.util.ArrayList;

public interface FileReaders {
    <T> ArrayList<T> read(File file);
}
