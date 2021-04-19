package org.openjfx.gui_utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.EditComponentPopupController;
import org.openjfx.data_models.PCComponents;

public class OpenEditComponentPopup {

    /** This method is responsible for opening a popup for editing the components in the tableview when double clicked */

    public static void editComponent(PCComponents componentToEdit, TableView<PCComponents> tableview, int index) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editComponentPopup.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Get the controller of another fxml to access it's methods
            EditComponentPopupController controller = fxmlLoader.getController();
            // Send the component to edit
            controller.setComponentToEdit(componentToEdit);
            // Listen to a click on the update button
            controller.getUpdatedComponentBtn().setOnMouseClicked(event -> {
                // Whenever update button is clicked, call the updateComponent() method
                controller.updateComponent();
                // Updated the changes to the tableview
                tableview.getItems().set(index, controller.getUpdatedComponent());
                // Close the popup window
                stage.close();
            });

            // Opens the popup window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Edit Components");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
