package org.openjfx.guiUtilities;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import org.openjfx.controllers.ControllerAdmin;
import org.openjfx.controllers.ControllerCustomer;

/** This class is responsible for updating the element indicators in the gui */

public class Indicators {

    /** labels at different views that needs to be updated */

    public static void updateFileNameAtAdmin(String filename){
        updateFilename(filename, ControllerAdmin.filenameLabelStatic);
    }

    public static void updateFileStatusAtAdmin(boolean isModified){
        updateFileStatus(isModified, ControllerAdmin.fileStatusStatic);
    }

    public static void updateFileNameAtUser(String filename){
        updateFilename(filename, ControllerCustomer.filenameStatic);
    }

    public static void updateFileStatusAtUser(boolean isModified){
        updateFileStatus(isModified, ControllerCustomer.fileStatusStatic);
    }


    /** Whenever a new file is opened, the filename of that file will be shown */
    private static void updateFilename(String filename, Label label){
        if(label != null) {
            label.setText(filename);
        }
    }

    /** Shows whether the current opened file is modified or not */
    private static void updateFileStatus(boolean isModified, Label label){
        if(label != null) {
            if(isModified) {
                label.setText("Modified");
                label.setStyle("-fx-text-fill: darkred");
            } else {
                label.setText("Saved");
                label.setStyle("-fx-text-fill: seagreen");
            }
        }
    }

    /** Shows tooltips on jfx controls when hovered upon */
    public static <T extends Control> void showToolTip(T object, String toolTipMessage){
        Tooltip tooltip = new Tooltip(toolTipMessage);
        object.setTooltip(tooltip);
        object.getTooltip().setStyle("-fx-text-fill: black; -fx-background-color: gold; -fx-font-size: 12px;");
    }
}
