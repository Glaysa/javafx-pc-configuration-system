package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.guiUtilities.AlertDialog;
import java.io.IOException;

/**
 * JAVAFX Application
 */

public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 1300, 900);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        stage.setOnCloseRequest(windowEvent -> stageClose(stage));
        App.stage = stage;
    }

    public static void setRoot(String fxml, String title) throws IOException {
        scene.setRoot(loadFXML(fxml));
        stage.setTitle(title);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void stageClose(Stage stage) {
        FileActions fileActions = new FileActions();
        if (ComponentsCollection.isModified()) {
            String response = AlertDialog.showConfirmDialog("Do you want to save your changes?");
            if (response.equals("Yes")) {
                fileActions.saveChanges(ComponentsCollection.getComponentArrayList(), "Saving changes...");
            }
        } else if (ConfigurationCollection.isModified()) {
            fileActions.saveChanges(ConfigurationCollection.getConfigsArrayList(), "Saving Configurations...");
        }
        stage.close();
    }

    public static void main(String[] args) {
        launch();
    }
}
