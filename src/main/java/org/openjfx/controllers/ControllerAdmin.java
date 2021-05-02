package org.openjfx.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.Indicators;
import org.openjfx.guiUtilities.popupDialogs.PopupForComponents;
import org.openjfx.guiUtilities.popupDialogs.PopupForTableView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAdmin implements Initializable {

    public static Label filenameLabelStatic;
    public static Label fileStatusStatic;
    @FXML private Label filenameLabel;
    @FXML private Label fileStatus;
    @FXML private TableView<PCComponents> tableView;
    @FXML private TextField searchInput;
    @FXML private TextField cName;
    @FXML private TextField price;
    @FXML private TextArea cDesc;
    @FXML private ComboBox<String> typeOptions;
    private final FileActions file = new FileActions();
    private final File defaultData = new File("src/main/java/database/initialComponents.txt");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Opens a file containing the default list of components.
        file.open(defaultData, "Loading system data...");
        // Initializes the tableview.
        ComponentsCollection.setTableView(tableView);
        // Fills the component type combobox with values.
        ComponentsCollection.fillComponentTypeObsList();
        ComponentsCollection.fillComponentTypeComboBox(typeOptions);
        // (listener) Initializes detection of a change on the component collection
        ComponentsCollection.collectionOnChange(typeOptions);
        // (listener) Initializes detection of double click on row of tableview
        editComponentOnDoubleClick();
        // Initializes tableview tooltips
        Indicators.showToolTip(tableView, "Double click to edit");
        // (listener) Initializes search functionality
        search();

        // Shows which file is opened and if it's saved or modified
        // Assigned to another static element to give access to other classes
        filenameLabelStatic = filenameLabel;
        fileStatusStatic = fileStatus;
    }

    /** Creates a new component to add on the tableview. */

    @FXML
    void createComponent() {
        try {
            int strNumber = PCComponents.createUniqueId();
            String strName = cName.getText();
            String strType = typeOptions.getValue();
            String strSpecs = cDesc.getText();
            String strPrice = price.getText();

            PCComponents c = new PCComponents(strNumber, strName, strType, strSpecs, strPrice);
            ComponentsCollection.addToCollection(c);
            resetFields();

            AlertDialog.showSuccessDialog("Component Added Successfully!");
        } catch (IllegalArgumentException e) {
            AlertDialog.showWarningDialog(e.getMessage(), "");
        }
    }

    /** Deletes all selected rows */

    @FXML
    void deleteComponent(){
        ObservableList<PCComponents> toDelete = tableView.getSelectionModel().getSelectedItems();
        String response = AlertDialog.showConfirmDialog("This cannot be undone! Are you sure?");
        if(response.equals("Yes")) ComponentsCollection.removeAllSelected(toDelete);
    }

    /** Opens a file through file chooser */

    @FXML
    void openFile(){
        FileChooser fileChooser = file.getFileChooser();
        File fileToOpen = fileChooser.showOpenDialog(new Stage());
        if(fileToOpen == null) {
            AlertDialog.showWarningDialog("No file was chosen","");
        } else {
            file.open(fileToOpen, "Opening file...");
        }
    }

    /** Saves a file through file chooser */

    @FXML
    void saveFile(){
        FileChooser fileChooser = file.getFileChooser();
        File fileToSave = fileChooser.showSaveDialog(new Stage());
        if(fileToSave == null) {
            AlertDialog.showWarningDialog("No file was chosen","");
        } else {
            file.save(ComponentsCollection.getComponentArrayList(), fileToSave, "Saving file...");
        }
    }

    /** Save all changes to the current opened file */

    @FXML
    void saveChanges(){
        file.saveChanges(ComponentsCollection.getComponentArrayList(), "Saving changes...");
    }

    /** Opens a popup window to let the admin filter the tableview */

    @FXML
    void filterTableView(){
        PopupForTableView.showFilterOptions(tableView);
    }

    /** Change view to login view */

    @FXML
    void logout() throws IOException {

        // Admin is prompted when there are unsaved changes
        if(ComponentsCollection.isModified()) {
            String response = AlertDialog.showConfirmDialog("Do you want to save your changes?");
            if(response.equals("Yes")) {
                file.saveChanges(ComponentsCollection.getComponentArrayList(), "Saving changes...");
            }
        }

        // Otherwise, the admin is logged out immediately
        ComponentsCollection.clearCollection();
        ComponentsCollection.setModified(false);
        App.setRoot("login");
    }

    /** Searches through the tableview with the given search input */
    void search() {
        ComponentsCollection.collectionSearch(searchInput, tableView);
    }

    /** Detects a double click on a row in the tableview
     * and opens a popup for component editing.*/

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
                        PopupForComponents.editComponent(componentToUpdate, tableView, index);
                    }
                }
            });
            return row;
        });
    }

    /** Resets all input fields after successful addition
     * of components and refreshes the tableview. */

    void resetFields() {
        cName.setText("");
        cDesc.setText("");
        price.setText("");
        tableView.refresh();
    }
}
