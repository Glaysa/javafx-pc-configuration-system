package org.openjfx.fileUtilities.FileHandlers;

import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.fileUtilities.FileRestrictions;
import org.openjfx.guiUtilities.AlertDialog;
import java.util.ArrayList;

/** This class is responsible for handling the contents of file when opened. */

public class FileContents extends FileRestrictions {

    /** Checks if the file contains a valid data */
    public static void processData(ArrayList<Object> data){
        try {
            if(data.isEmpty()) {
                throw new IllegalArgumentException("File is empty!");
            } else {
                if(data.get(0) instanceof String) {
                    FileRestrictions.parseAndCheckRestrictions(data.get(0).toString());
                    System.out.println("String");
                } else if(data.get(0) instanceof PCComponents) {
                    FileRestrictions.checkRestrictions(PCComponents.getObjectIDKey());
                    System.out.println("Components");
                } else if(data.get(0) instanceof PCConfigurations) {
                    FileRestrictions.checkRestrictions(PCConfigurations.getObjectIDKey());
                    System.out.println("Configurations");
                } else {
                    throw new IllegalArgumentException("The system cannot read the file contents!");
                }
            }
        } catch (Exception e) {
            AlertDialog.showWarningDialog(e.getMessage(),"Please try again!");
        }
    }
}
