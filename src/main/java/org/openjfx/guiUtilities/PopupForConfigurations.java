package org.openjfx.guiUtilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.PopupNewConfigurationController;
import java.io.IOException;

public class PopupForConfigurations {

    public static void newConfiguration(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("popupNewConfiguration.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            PopupNewConfigurationController controller = fxmlLoader.getController();
            controller.loadConfigurationOptions();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("PC Configuration");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
