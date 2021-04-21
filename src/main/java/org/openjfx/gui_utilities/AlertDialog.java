package org.openjfx.gui_utilities;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** This class is responsible for opening dialogs of success, warning, confirmation and loading operations. */

public class AlertDialog {

    public static void showSuccessDialog(String dialogMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(dialogMessage);
        alert.setTitle("Success");
        alert.showAndWait();
    }

    public static void showWarningDialog(String dialogMessage, String dialogContent){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(dialogMessage);
        alert.setContentText(dialogContent);
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
        // Children
        ProgressBar p = new ProgressBar();
        p.progressProperty().bind(task.progressProperty());
        p.setPrefWidth(300);
        p.setPrefHeight(15);
        Label label = new Label("Thread running...");
        label.setStyle("-fx-padding: 10px 0 0 0; -fx-font-size: 14px; -fx-text-fill: blue");

        // Parent
        VBox box = new VBox();
        box.getChildren().addAll(p, label);

        // Alert Dialog
        ButtonType ok = new ButtonType("Ok");
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText(dialogMessage);
        alert.getButtonTypes().setAll(ok);
        alert.setTitle("Loading");
        alert.getDialogPane().setContent(box);
        alert.getDialogPane().setPrefWidth(300);
        alert.getDialogPane().setPrefHeight(175);
        alert.getDialogPane().lookupButton(ok).setDisable(true);

        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.setOnCloseRequest(Event::consume);

        return alert;
    }
}
