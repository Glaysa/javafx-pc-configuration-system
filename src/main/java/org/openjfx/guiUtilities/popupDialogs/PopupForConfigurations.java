package org.openjfx.guiUtilities.popupDialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.popupControllers.PopupNewConfigurationController;
import java.io.IOException;

public class PopupForConfigurations {

    public static void newConfiguration(){
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupNewConfiguration");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
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
