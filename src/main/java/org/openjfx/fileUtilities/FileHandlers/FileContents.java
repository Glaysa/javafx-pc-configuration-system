package org.openjfx.fileUtilities.FileHandlers;

import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.fileUtilities.FileParser;
import org.openjfx.guiUtilities.AlertDialog;
import java.util.ArrayList;

/** This class is responsible for handling the contents of file when opened. */

public class FileContents {

    private static String restrictedDataKey;

    /** Checks if the file contains the expected data */
    public static void processData(ArrayList<Object> data){
        try {
            if(data.isEmpty()) {
                throw new IllegalArgumentException("File is empty!");
            } else {
                if(data.get(0) instanceof String) {
                    System.out.println("String Object");
                    FileParser.objectParser(data.get(0).toString(), restrictedDataKey);
                }
                else if(data.get(0) instanceof PCComponents) {
                    System.out.println("Component Object");
                }
                else if(data.get(0) instanceof PCConfigurations) {
                    System.out.println("Configuration Object");
                }
                else {
                    System.out.println("Cannot read file contents!");
                }
            }
        } catch (Exception e) {
            AlertDialog.showWarningDialog(e.getMessage(),"Please try again!");
        }
    }

    public static void setRestrictedDataKey(String objectIDKey){
        restrictedDataKey = objectIDKey;
    }
}
