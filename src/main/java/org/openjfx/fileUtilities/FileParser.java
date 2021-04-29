package org.openjfx.fileUtilities;

import com.google.gson.Gson;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.AlertDialog;

/** This class is responsible for parsing data. */

public class FileParser {

    public static Object convertToObject(String dataToParse) {
        Gson gson = new Gson();
        if(dataToParse.contains(PCConfigurations.getClassReference())) {
            return gson.fromJson(dataToParse, PCConfigurations.class);
        } else if(dataToParse.contains(PCComponents.getClassReference())) {
            return gson.fromJson(dataToParse, PCComponents.class);
        } else {
            AlertDialog.showWarningDialog("Invalid data","");
            return null;
        }
    }
}