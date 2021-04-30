package org.openjfx.guiUtilities.popupDialogs;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PopupUtilities {

    /** Returns the popup file view path */
    public static String getPopupPath(String fxml){
        return "popupViews/" + fxml + ".fxml";
    }

    /** Closes given popup */
    public static void closePopup(Pane parentPane){
        Stage stage = (Stage) parentPane.getScene().getWindow();
        stage.close();
    }
}
