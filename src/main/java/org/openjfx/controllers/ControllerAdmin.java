package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.file_utilities.FileHandlers.FileActions;
import org.openjfx.gui_utilities.AlertDialog;
import org.openjfx.gui_utilities.OpenEditComponentPopup;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdmin implements Initializable {

    @FXML private TableView<PCComponents> tableView;
    @FXML private TextField searchInput;
    @FXML private TextField cName;
    @FXML private TextField price;
    @FXML private TextArea cDesc;
    @FXML private ComboBox<String> typeOptions;
    private final FileActions<PCComponents> file = new FileActions<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Opens a file containing the default list of components.
        file.open("initialComponents.txt", "Loading system data...");
        // Initializes the tableview.
        ComponentsCollection.setTableView(tableView);
        // Fills the component type combobox with values.
        ComponentsCollection.fillCombobox_TYPE(typeOptions);
        // (listener) Initializes detection of a change on the component collection
        ComponentsCollection.collectionOnChange(typeOptions);
        // (listener) Initializes detection of double click on row of tableview
        editComponentOnDoubleClick();
    }

    /** Creates a new component to add on the tableview. */
    @FXML
    void createComponent() {
        int strNumber = PCComponents.createUniqueId();
        String strName = cName.getText();
        String strType = typeOptions.getValue();
        String strSpecs = cDesc.getText();
        String strPrice = price.getText();

        try {
            PCComponents c = new PCComponents(strNumber, strName, strType, strSpecs, strPrice);
            ComponentsCollection.addToCollection(c);
            resetFields();
            AlertDialog.showSuccessDialog("Component Added Successfully!");
        } catch (IllegalArgumentException e) {
            AlertDialog.showWarningDialog(e.getMessage(), "");
        }
    }

    /** Resets all input fields after successful creation
     * of components and refreshes the tableview. */
    void resetFields() {
        cName.setText("");
        cDesc.setText("");
        price.setText("");
        tableView.refresh();
    }

    /** Detects a double click on a row in the tableview
     * and opens a new window for component editing.*/
    public void editComponentOnDoubleClick(){
        tableView.setRowFactory(tv -> {
            TableRow<PCComponents> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2) {
                    if(row.getItem() != null) {
                        // Index of the component to edit
                        int index = row.getIndex();
                        // Component to edit
                        PCComponents componentToUpdate = row.getItem();
                        // Opens a popup window for component editing
                        OpenEditComponentPopup.editComponent(componentToUpdate, tableView, index);
                    }
                }
            });
            return row;
        });
    }

    /** search() - searches through the tableview with the given search word. */
    @FXML
    void search() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
