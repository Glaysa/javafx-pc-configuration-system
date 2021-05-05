package org.openjfx.fileUtilities;

import com.google.gson.Gson;
import org.openjfx.dataModels.PCComponents;

/** This class is responsible for parsing data. */

public class FileParser {

    public static Object convertToObject(String dataToParse) {
        Gson gson = new Gson();
        return gson.fromJson(dataToParse, PCComponents.class);
    }
}