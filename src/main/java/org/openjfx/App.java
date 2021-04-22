package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.file_utilities.FileHandlers.FileActions;
import org.openjfx.gui_utilities.AlertDialog;
import java.io.IOException;

/** JAVAFX Application */

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 1300, 800);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> stageClose(stage));
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void stageClose(Stage stage){
        if(ComponentsCollection.isModified()) {
            String response = AlertDialog.showConfirmDialog("Do you want to save your changes?");
            if(response.equals("Yes")) {
                FileActions<PCComponents> fileActions = new FileActions<>();
                fileActions.saveChanges(ComponentsCollection.getComponentObsList());
            }
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
