package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.file_utilities.FileHandlers.FileActions;
import org.openjfx.gui_utilities.Dialogs;
import org.openjfx.gui_utilities.OpenPopup;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdmin implements Initializable {

    @FXML private TableView<PCComponents> tableView;
    @FXML private TextField searchInput;
    @FXML private TextField cNumber;
    @FXML private TextField cName;
    @FXML private TextField price;
    @FXML private TextArea cDesc;
    @FXML private ComboBox<String> typeOptions;
    private final FileActions<PCComponents> file = new FileActions<>();
    private PCComponents updatedComponent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Opens a file containing the default lists of components.
        file.open("initialComponents.txt", "Loading system data...");
        // Initializes the tableview.
        ComponentsCollection.setTableView(tableView);
        // Fills the component type combobox with values.
        ComponentsCollection.fillCombobox_TYPE(typeOptions);
        // (listener) Whenever there is a new component type, they are added to the component type combobox
        ComponentsCollection.fillCombobox_TYPE_listOnChanged(typeOptions);
        // (listener) Initializes detection of double click on row of tableview
        editComponentOnDoubleClick();
    }

    /** Creates a new component to add on the tableview. */

    @FXML
    void createComponent() {
        String componentNumber = cNumber.getText();
        String componentName = cName.getText();
        String componentSpecs = cDesc.getText();
        String componentPrice = price.getText();
        String componentType = typeOptions.getValue();

        try {
            PCComponents c = new PCComponents(componentNumber, componentName, componentType, componentSpecs, componentPrice);
            ComponentsCollection.addToCollection(c);
            resetFields();

            Dialogs.showSuccessDialog("Component Added Successfully!");
        } catch (IllegalArgumentException e) {
            Dialogs.showWarningDialog(e.getMessage(), "");
        }
    }

    /** Resets all input fields after successful creation of components and refreshes the tableview. */

    void resetFields() {
        cName.setText("");
        cDesc.setText("");
        price.setText("");
        tableView.refresh();
    }

    /** Detects a double click on a row in the tableview and opens a new window for component editing.*/

    public void editComponentOnDoubleClick(){
        tableView.setRowFactory(tv -> {
            TableRow<PCComponents> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2) {
                    if(row.getItem() != null) {
                        // Index of the component to edit
                        int index = row.getIndex();
                        // Get the component to edit
                        PCComponents componentToUpdate = row.getItem();
                        // Opens a popup window for component editing
                        OpenPopup.editComponent(componentToUpdate, tableView, index);
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
