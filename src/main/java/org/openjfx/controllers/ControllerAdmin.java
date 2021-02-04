package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.gui_utilities.Dialogs;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdmin implements Initializable {

    @FXML private TableView<PCComponents> tableView;
    @FXML private TextField searchInput;
    @FXML private TextField cName;
    @FXML private TextField price;
    @FXML private TextArea cDesc;
    @FXML private ComboBox<?> cType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComponentsCollection.setTableView(tableView);
    }

    @FXML
    void addComponent() {
        String componentName = cName.getText();
        String componentDesc = cDesc.getText();
        String componentPrice = price.getText();

        try {
            PCComponents c = new PCComponents(componentName,componentDesc,"type",componentPrice);
            ComponentsCollection.addToCollection(c);
            resetFields();

            Dialogs.showSuccessDialog("Component Added Successfully!");
        } catch (IllegalArgumentException e) {
            Dialogs.showWarningDialog(e.getMessage());
        }
    }

    @FXML
    void search(){
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    void resetFields(){
        cName.setText("");
        cDesc.setText("");
        price.setText("");
        tableView.refresh();
    }
}
