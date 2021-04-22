package org.openjfx.fileUtilities;

import java.io.File;
import java.util.ArrayList;

public interface FileReaders {
    <T> ArrayList<T> read(File file);
}
