package org.openjfx.guiUtilities;

import org.openjfx.controllers.ControllerAdmin;

/** Class responsible for updating the element indicators in the gui */
public class Indicators {

    /** Whenever a new file is opened, the filename of that file will be shown */
    public static void updateFilename(String filename){
        ControllerAdmin.filenameLabelStatic.setText(filename);
    }

    /** Shows whether the current opened file is modified or not */
    public static void updateFileStatus(boolean isModified){
        if(isModified) {
            ControllerAdmin.fileStatusStatic.setText("Modified");
            ControllerAdmin.fileStatusStatic.setStyle("-fx-text-fill: darkred");
        } else {
            ControllerAdmin.fileStatusStatic.setText("Saved");
            ControllerAdmin.fileStatusStatic.setStyle("-fx-text-fill: seagreen");
        }
    }
}
