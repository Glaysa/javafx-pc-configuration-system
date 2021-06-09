package org.openjfx.guiUtilities.popupDialogs;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.popupControllers.PopupCompareComponentsController;
import org.openjfx.controllers.popupControllers.PopupEditComponentController;
import org.openjfx.controllers.popupControllers.PopupShowComponentController;
import org.openjfx.dataModels.PCComponents;

/** This class is responsible of opening the popups related to components */

public class PopupForComponents {

    /** Opens a popup window to edit components */
    public static void editComponent(PCComponents componentToEdit, TableView<PCComponents> tableview, int index) {
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupEditComponent");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Get the controller of another fxml to access it's methods
            PopupEditComponentController controller = fxmlLoader.getController();
            // Send the component to edit
            controller.setComponentToEdit(componentToEdit);
            // Listen to a click on the update button
            controller.getUpdateComponentBtn().setOnAction(event -> {
                // Whenever update button is clicked, call the updateComponent() method
                controller.updateComponent();
                // Update the changes to the tableview
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

    /** Opens a popup window to show component details */
    public static void showComponent(PCComponents componentToShow, TableView<PCComponents> tv, String condition) {
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupShowComponent");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Get the controller of another fxml to access it's methods
            PopupShowComponentController controller = fxmlLoader.getController();
            // Send the component to show
            controller.setComponentToShow(componentToShow);
            // Depending on which table view we are on, different button will show up
            if(tv != null) {
                switch (tv.getId()) {
                    case "configurationsSelectTableView":
                        controller.setButtonAsSelect(componentToShow);
                        break;
                    case "componentsTableView":
                        controller.setButtonAsAddToCart(componentToShow);
                        break;
                    case "componentsCartTableView":
                        controller.setButtonAsRemoveFromCart(componentToShow);
                        break;
                    default:
                        controller.hideButton();
                        break;
                }
            // If tv is not given, the param condition will be checked instead
            } else {
                if(condition.equals("configurationItem")) {
                    controller.setButtonAsRemoveConfigItem(componentToShow);
                }
            }

            // Opens the popup window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Component Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Opens a popup window to show components to compare */
    public static void compareComponent(ObservableList<PCComponents> components){
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupCompareComponents");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Get the controller of another fxml to access it's methods
            PopupCompareComponentsController controller = fxmlLoader.getController();
            // Load component details to compare
            for(PCComponents component: components) controller.loadComponentsToCompare(component);
            // Add to hBox as its children
            controller.compareComponents();

            // Opens the popup window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.setTitle("Compare Components");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Detects a double click on a row in the tableview
     * and opens a new window to show the component details.*/

    public static void showComponentDetails(TableView<PCComponents> tableView){
        tableView.setRowFactory(tv -> {
            TableRow<PCComponents> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2) {
                    if(row.getItem() != null) {
                        // Component to show
                        PCComponents componentToShow = row.getItem();
                        // opens a popup window to show component details
                        PopupForComponents.showComponent(componentToShow, tableView,"");
                    }
                }
            });
            return row;
        });
    }
}
