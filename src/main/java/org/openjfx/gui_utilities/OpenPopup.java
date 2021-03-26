package org.openjfx.gui_utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.PopupControllerEditComponents;
import org.openjfx.data_models.PCComponents;

public class OpenPopup {

    /** This method is responsible for opening a popup for editing the components in the tableview when double clicked */

    public static PCComponents editComponent(PCComponents componentToEdit, TableView<PCComponents> tableview){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editComponentsPopup.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Get the controller of another fxml to access it's methods
            PopupControllerEditComponents controller = fxmlLoader.getController();
            // Send the component to edit
            controller.setComponentToEdit(componentToEdit);
            // Listen to a click on the update button
            controller.getUpdatedComponentBtn().setOnMouseClicked(event -> {
                // Whenever update button is clicked, call the updateComponent() method
                controller.updateComponent();
                // Refresh the tableview to see the changes
                tableview.refresh();
                // Close the popup window
                stage.close();
            });

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Edit Components");
            stage.show();

            // Return the new updated component
            return controller.getUpdatedComponent();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
