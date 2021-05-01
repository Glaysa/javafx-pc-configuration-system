package org.openjfx.guiUtilities.popupDialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.popupControllers.PopupNewConfigurationController;
import org.openjfx.controllers.popupControllers.PopupShowConfigurationController;
import org.openjfx.dataModels.PCConfigurations;
import java.io.IOException;

/** This class is responsible of opening the popups related to components */

public class PopupForConfigurations {

    /** Opens a popup window to let the user add a new configured PC */
    public static void newConfiguration(){
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupNewConfiguration");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Get the controller of another fxml to access it's methods
            PopupNewConfigurationController controller = fxmlLoader.getController();
            // Loads buttons as configuration options
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

    /** Opens a popup window to let the user see the configured PC details */
    private static void showConfiguredPC(PCConfigurations toShow, TableView<PCConfigurations> tv){
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupShowConfiguration");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Get the controller of another fxml to access it's methods
            PopupShowConfigurationController controller = fxmlLoader.getController();
            // Send configured pc details
            controller.loadConfigurationDetails(toShow);
            // Depending on which tableview the user is on, different buttons is shown
            if(tv.getId().equals("configurationCartTableView")) {
                controller.setButtonAsRemoveFromCart(toShow);
            } else {
                controller.setButtonAsAddToCart(toShow);
            }

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("PC Configuration");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Detects a double click on a row in the tableview
     * and opens a new window to show the configured PC details.*/

    public static void showConfigurationDetails(TableView<PCConfigurations> tableViewConfigurations){
        tableViewConfigurations.setRowFactory(tv -> {
            TableRow<PCConfigurations> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        // Component to edit
                        PCConfigurations configItem = row.getItem();
                        // opens a popup window to show component details
                        showConfiguredPC(configItem, tableViewConfigurations);
                    }
                }
            });
            return row;
        });
    }
}
