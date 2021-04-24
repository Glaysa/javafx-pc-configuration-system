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
import org.openjfx.guiUtilities.PopupEditComponent;
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
    private final FileActions<PCComponents> file = new FileActions<>();
    private final File defaultData = new File("src/main/java/database/initialComponents.txt");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Opens a file containing the default list of components.
        file.open(defaultData, "Loading system data...");
        // Initializes the tableview.
        ComponentsCollection.setTableView(tableView);
        // Fills the component type combobox with values.
        ComponentsCollection.fillCombobox_TYPE(typeOptions);
        // (listener) Initializes detection of a change on the component collection
        ComponentsCollection.collectionOnChange(typeOptions);
        // (listener) Initializes detection of double click on row of tableview
        editComponentOnDoubleClick();
        // Initializes tableview tooltips
        Indicators.showToolTip(tableView, "Double click to edit");

        // Shows which file is opened and it's saved or modified
        // Assign to another static element to give access to other classes
        filenameLabelStatic = filenameLabel;
        fileStatusStatic = fileStatus;
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
        FileChooser fileChooser = getFileChooser();
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
        FileChooser fileChooser = getFileChooser();
        File fileToSave = fileChooser.showSaveDialog(new Stage());
        if(fileToSave == null) {
            AlertDialog.showWarningDialog("No file was chosen","");
        } else {
            file.save(ComponentsCollection.getComponentObsList(), fileToSave, "Saving file...");
        }
    }

    /** Save all changes to the current opened file */

    @FXML
    void saveChanges(){
        file.saveChanges(ComponentsCollection.getComponentObsList(), "Saving changes...");
    }

    /** search() - searches through the tableview with the given search word. */

    @FXML
    void search() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /** Change view to login view */

    @FXML
    void logout() throws IOException {
        if(ComponentsCollection.isModified()) {
            String response = AlertDialog.showConfirmDialog("Do you want to save your changes?");
            if(response.equals("Yes")) {
                file.saveChanges(ComponentsCollection.getComponentObsList(), "Saving changes...");
            }
            ComponentsCollection.clearCollection();
            App.setRoot("login");
        }
    }

    /** Initializes a file chooser */

    private FileChooser getFileChooser(){
        File initialDir = new File(System.getProperty("user.home") + "\\Desktop");
        FileChooser.ExtensionFilter f1 = new FileChooser.ExtensionFilter("All Files", "*.*");
        FileChooser.ExtensionFilter f2 = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        FileChooser.ExtensionFilter f3 = new FileChooser.ExtensionFilter("Binary Files", "*.bin");
        FileChooser.ExtensionFilter f4 = new FileChooser.ExtensionFilter("Jobj Files", "*.obj");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialDir);
        fileChooser.getExtensionFilters().addAll(f1, f2, f3, f4);
        return fileChooser;
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
                        PopupEditComponent.editComponent(componentToUpdate, tableView, index);
                    }
                }
            });
            return row;
        });
    }

    /** Resets all input fields after successful creation
     * of components and refreshes the tableview. */

    void resetFields() {
        cName.setText("");
        cDesc.setText("");
        price.setText("");
        tableView.refresh();
    }
}
