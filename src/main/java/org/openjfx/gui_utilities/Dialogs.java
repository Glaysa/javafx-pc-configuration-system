package org.openjfx.gui_utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialogs {

    public static void showSuccessDialog(String dialogMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(dialogMessage);
        alert.setTitle("Success");
        alert.showAndWait();
    }

    public static void showWarningDialog(String dialogMessage){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(dialogMessage);
        alert.setTitle("Warning");
        alert.showAndWait();
    }

    public static String showConfirmDialog(String dialogMessage){
        ButtonType y = new ButtonType("Yes");
        ButtonType n = new ButtonType("No");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(dialogMessage);
        alert.getButtonTypes().setAll(y,n);
        alert.setTitle("Confirm");
        alert.showAndWait();

        return alert.getResult().getText();
    }
}
