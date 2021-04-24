package org.openjfx.fileUtilities;

import com.google.gson.Gson;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.AlertDialog;

/** This class is responsible for parsing data. */

public class FileParser {

    public static Object convertToObject(String dataToParse){
        Gson gson = new Gson();
        String[] attributes = dataToParse.split(",");
        if(dataToParse.contains(PCComponents.getClassReference())) {
            if(attributes.length == 5) {
                return gson.fromJson(dataToParse, PCComponents.class);
            } else {
                AlertDialog.showWarningDialog("File is corrupted", "Please open another file");
            }
        } else {
            AlertDialog.showWarningDialog("File contains invalid data", "Class reference not found");
        }
        return null;
    }
}
