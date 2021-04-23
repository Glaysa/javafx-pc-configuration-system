package org.openjfx.fileUtilities;

import com.google.gson.Gson;
import org.openjfx.dataModels.PCComponents;

/** This class is responsible for parsing data. */

public class FileParser {

    public static Object convertToObject(String dataToParse){
        Gson gson = new Gson();
        String[] attributes = dataToParse.split(",");
        if(dataToParse.contains(PCComponents.getClassReference())) {
            if(attributes.length == 5) {
                return gson.fromJson(dataToParse, PCComponents.class);
            }
        }
        return null;
    }
}
