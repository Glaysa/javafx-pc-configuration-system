package org.openjfx.guiUtilities.popupDialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.popupControllers.PopupFilterTableViewController;
import org.openjfx.dataModels.PCComponents;

public class PopupForTableView {

    /** Opens a popup window to let the user/admin filter the tableview */
    public static void showFilterOptions(TableView<PCComponents> tableView){
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupFilterTableView");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            PopupFilterTableViewController controller = fxmlLoader.getController();
            controller.setTableView(tableView);
            controller.createFilterCheckboxes();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Filter Options");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
