package org.openjfx.guiUtilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.dataModels.PCComponents;

public class PopupForTableView {

    public static void showFilterOptions(TableView<PCComponents> tableView){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("popupFilterTableView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
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
