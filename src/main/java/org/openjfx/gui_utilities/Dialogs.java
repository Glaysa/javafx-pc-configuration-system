package org.openjfx.gui_utilities;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

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

    public synchronized static <V> Alert showLoadingDialog(Task<V> task, String dialogMessage){
        ProgressBar p = new ProgressBar();
        p.progressProperty().bind(task.progressProperty());

        ButtonType ok = new ButtonType("Ok");
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText(dialogMessage);
        alert.getButtonTypes().setAll(ok);
        alert.setTitle("Loading");
        alert.getDialogPane().setContent(p);
        alert.getDialogPane().setPrefWidth(300);
        alert.getDialogPane().setPrefHeight(120);
        alert.getDialogPane().lookupButton(ok).setDisable(true);

        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.setOnCloseRequest(Event::consume);

        return alert;
    }
}
