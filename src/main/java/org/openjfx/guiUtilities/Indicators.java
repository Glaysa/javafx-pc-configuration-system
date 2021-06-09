package org.openjfx.guiUtilities;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import org.openjfx.controllers.ControllerAdmin;
import org.openjfx.controllers.ControllerCustomer;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;

/** This class is responsible for updating the element indicators in the gui */

public class Indicators {

    /** labels at different views that needs to be updated */

    public static void updateFileNameAtAdmin(String filename){
        updateFilename(filename, ControllerAdmin.filenameLabelStatic);
    }

    public static void updateFileNameAtUser(String filename){
        updateFilename(filename, ControllerCustomer.filenameStatic);
    }

    public static void updateFileStatusAtAdmin(boolean isModified){
        updateFileStatus(isModified, ControllerAdmin.fileStatusStatic);
        ComponentsCollection.setModified(isModified);
    }

    public static void updateFileStatusAtUser(boolean isModified){
        updateFileStatus(isModified, ControllerCustomer.fileStatusStatic);
        ConfigurationCollection.setModified(isModified);
    }

    public static void updateAllFileStatuses(){
        updateFileStatusAtUser(false);
        updateFileStatusAtAdmin(false);
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
                label.setStyle("-fx-text-fill: red");
            } else {
                label.setText("Saved");
                label.setStyle("-fx-text-fill: yellowgreen");
            }
        }
    }

    /** Shows tooltips on jfx controls when hovered upon */
    public static <T extends Control> void showToolTip(T object, String toolTipMessage){
        Tooltip tooltip = new Tooltip(toolTipMessage);
        object.setTooltip(tooltip);
        object.getTooltip().setStyle("-fx-text-fill: black; -fx-background-color: gold; -fx-font-size: 12px;");
    }

    public static void showToolTip(Node node, String toolTipMessage){
        Tooltip tooltip = new Tooltip(toolTipMessage);
        tooltip.setStyle("-fx-text-fill: black; -fx-background-color: gold; -fx-font-size: 12px;");
        Tooltip.install(node, tooltip);
    }
}
