package org.openjfx.fileUtilities.FileHandlers;

import org.openjfx.guiUtilities.AlertDialog;
import java.util.ArrayList;

/** This class is responsible for handling the contents of file when opened. */

public class FileContents {

    /** Checks if the file contains the expected data */
    public static void processData(ArrayList<Object> data){
        try {
            if(data.isEmpty()) {
                throw new IllegalArgumentException("File is empty!");
            } else {
                System.out.println(data);
            }
        } catch (Exception e) {
            AlertDialog.showWarningDialog(e.getMessage(),"Please try again!");
        }
    }
}
