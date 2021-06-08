package org.openjfx.guiUtilities;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.App;

import java.util.Objects;

/** This class is responsible for opening dialogs of success, warning, confirmation and loading operations. */

public class AlertDialog {

    static Alert success = new Alert(Alert.AlertType.INFORMATION);
    static Alert warning = new Alert(Alert.AlertType.WARNING);
    static Alert confirm = new Alert(Alert.AlertType.INFORMATION);

    public static void showSuccessDialog(String dialogMessage){
        success.setHeaderText(dialogMessage);
        success.setTitle("Success");
        success.showAndWait();
    }

    public static void showWarningDialog(String dialogMessage, String dialogContent){
        warning.setHeaderText(dialogMessage);
        warning.setContentText(dialogContent);
        warning.setTitle("Warning");
        warning.showAndWait();
    }

    public static String showConfirmDialog(String dialogMessage){
        ButtonType y = new ButtonType("Yes");
        ButtonType n = new ButtonType("No");

        confirm.setHeaderText(dialogMessage);
        confirm.getButtonTypes().setAll(y,n);
        confirm.setTitle("Confirm");
        confirm.showAndWait();

        return confirm.getResult().getText();
    }

    public static <V> Alert showLoadingDialog(Task<V> task, String dialogMessage){
        // Children
        ProgressBar p = new ProgressBar();
        p.progressProperty().bind(task.progressProperty());
        p.setPrefWidth(300);
        p.setPrefHeight(15);
        Label label = new Label("Thread running...");
        label.setStyle("-fx-padding: 10px 0 0 0; -fx-font-size: 14px; -fx-text-fill: gold");

        // Parent
        VBox box = new VBox();
        box.getChildren().addAll(p, label);

        // Alert Dialog
        ButtonType ok = new ButtonType("Ok");
        Alert loading = new Alert(Alert.AlertType.NONE);
        loading.setHeaderText(dialogMessage);
        loading.getButtonTypes().setAll(ok);
        loading.setTitle("Loading");
        loading.getDialogPane().setContent(box);
        loading.getDialogPane().setPrefWidth(300);
        loading.getDialogPane().setPrefHeight(175);
        loading.getDialogPane().lookupButton(ok).setDisable(true);

        Stage s = (Stage) loading.getDialogPane().getScene().getWindow();
        s.setOnCloseRequest(Event::consume);

        DialogPane loadingPane = loading.getDialogPane();
        loadingPane.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/styles.css")).toExternalForm());
        loadingPane.getStyleClass().add("loading");

        return loading;
    }

    public static void applyAlertStyles(){
        DialogPane successPane = success.getDialogPane();
        successPane.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/styles.css")).toExternalForm());
        successPane.getStyleClass().add("success");
        ImageView successIcon = new ImageView(Objects.requireNonNull(App.class.getResource("images/success.png")).toExternalForm());
        successIcon.setFitWidth(50);
        successIcon.setFitHeight(50);
        successPane.setGraphic(successIcon);

        DialogPane warningPane = warning.getDialogPane();
        warningPane.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/styles.css")).toExternalForm());
        warningPane.getStyleClass().add("warning");
        ImageView warningIcon = new ImageView(Objects.requireNonNull(App.class.getResource("images/warning.png")).toExternalForm());
        warningIcon.setFitWidth(50);
        warningIcon.setFitHeight(50);
        warningPane.setGraphic(warningIcon);

        DialogPane confirmPane = confirm.getDialogPane();
        confirmPane.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/styles.css")).toExternalForm());
        confirmPane.getStyleClass().add("confirm");
        ImageView confirmIcon = new ImageView(Objects.requireNonNull(App.class.getResource("images/confirm.png")).toExternalForm());
        confirmIcon.setFitWidth(50);
        confirmIcon.setFitHeight(50);
        confirmPane.setGraphic(confirmIcon);
    }
}
