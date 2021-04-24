package org.openjfx.guiUtilities;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import org.openjfx.controllers.ControllerAdmin;
import org.openjfx.dataCollection.ComponentsCollection;

/** Class responsible for updating the element indicators in the gui */
public class Indicators {

    /** Whenever a new file is opened, the filename of that file will be shown */
    public static void updateFilename(String filename){
        ControllerAdmin.filenameLabelStatic.setText(filename);
    }

    /** Shows whether the current opened file is modified or not */
    public static void updateFileStatus(boolean isModified){
        ComponentsCollection.setModified(isModified);
        if(isModified) {
            ControllerAdmin.fileStatusStatic.setText("Modified");
            ControllerAdmin.fileStatusStatic.setStyle("-fx-text-fill: darkred");
        } else {
            ControllerAdmin.fileStatusStatic.setText("Saved");
            ControllerAdmin.fileStatusStatic.setStyle("-fx-text-fill: seagreen");
        }
    }

    /** Shows tooltips on jfx controls when hovered upon */
    public static <T extends Control> void showToolTip(T object, String toolTipMessage){
        Tooltip tooltip = new Tooltip(toolTipMessage);
        object.setTooltip(tooltip);
        object.getTooltip().setStyle("-fx-text-fill: black; -fx-background-color: gold; -fx-font-size: 12px;");
    }
}
