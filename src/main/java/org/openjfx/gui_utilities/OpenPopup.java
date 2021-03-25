package org.openjfx.gui_utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.PopupControllerEditComponents;
import org.openjfx.data_models.PCComponents;

public class OpenPopup {

    /** This class is responsible for opening the window for editing the items in the tableview when double clicked. */

    public static void editComponent(PCComponents componentToEdit){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editComponentsPopup.fxml"));
            Parent root = fxmlLoader.load();

            PopupControllerEditComponents controller = fxmlLoader.getController();
            controller.setComponentToEdit(componentToEdit);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
