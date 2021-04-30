package org.openjfx.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCartCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.guiUtilities.*;
import org.openjfx.guiUtilities.popupDialogs.PopupForComponents;
import org.openjfx.guiUtilities.popupDialogs.PopupForConfigurations;
import org.openjfx.guiUtilities.popupDialogs.PopupForTableView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {

    @FXML private Label totalPriceLabel;
    @FXML private TextField searchInput;
    @FXML private TableView<PCComponents> tableViewCartComponents;
    @FXML private TableView<PCComponents> tableViewComponents;
    @FXML private TableView<PCConfigurations> tableViewCartConfigurations;
    @FXML private TableView<PCConfigurations> tableViewConfigurations;
    private final FileActions<PCComponents> file = new FileActions<>();
    private final FileActions<PCConfigurations> fileConfig = new FileActions<>();
    private final File defaultData = new File("src/main/java/database/initialComponents.txt");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Opens a file containing the default list of components.
        file.open(defaultData, "Loading products...");

        // (listener) Initializes detection of a change on the cart collection
        ComponentsCartCollection.collectionOnChange(totalPriceLabel);
        ConfigurationCartCollection.collectionOnChange(totalPriceLabel);

        // Initializes the tableviews
        ComponentsCollection.setTableView(tableViewComponents);
        ComponentsCartCollection.setTableView(tableViewCartComponents);
        ConfigurationCollection.setTableView(tableViewConfigurations);
        ConfigurationCartCollection.setTableView(tableViewCartConfigurations);

        // (listener)
        ComponentsCollection.collectionOnChange(null);
        // Fills component type obs list
        ComponentsCollection.fillComponentTypeObsList();
        // (listener) Initializes detection of double click on row of tableview
        PopupForComponents.showComponentOnDoubleClick(tableViewComponents);
        PopupForComponents.showComponentOnDoubleClick(tableViewCartComponents);
        // Initializes tableview tooltips
        Indicators.showToolTip(tableViewComponents, "Double click to see component details");
        Indicators.showToolTip(tableViewCartComponents, "Double click to see component details");
        // (listener) Initializes search functionality
        search();
    }

    /** Add components to cart */

    @FXML
    void addToCart() {
        ObservableList<PCComponents> selected = tableViewComponents.getSelectionModel().getSelectedItems();
        if(!selected.isEmpty()) for(PCComponents toAdd : selected) ComponentsCartCollection.addToCollection(toAdd);
        else AlertDialog.showWarningDialog("Please select a component to add","");
        tableViewCartComponents.refresh();
    }

    /** Removes a component from the cart */

    @FXML
    void removeFromCart() {
        PCComponents selected = tableViewCartComponents.getSelectionModel().getSelectedItem();
        ComponentsCartCollection.removeSelected(selected);
        tableViewCartComponents.refresh();
    }

    /** Adds configured pc to cart as well */

    @FXML
    void addConfigToCart(){
        PCConfigurations selected = tableViewConfigurations.getSelectionModel().getSelectedItem();
        if(selected != null) ConfigurationCartCollection.addToCollection(selected);
        else AlertDialog.showWarningDialog("Choose a configured PC to add", "");
        tableViewCartConfigurations.refresh();
    }

    @FXML
    void checkout() {

    }

    /** Opens a file through file chooser */

    @FXML
    void openFile() {
        FileChooser fileChooser = fileConfig.getFileChooser();
        File fileToOpen = fileChooser.showOpenDialog(new Stage());
        if(fileToOpen == null) {
            AlertDialog.showWarningDialog("No file was chosen","");
        } else {
            fileConfig.open(fileToOpen, "Opening file...");
        }
    }

    /** Saves a file through file chooser */

    @FXML
    void saveFile() {
        FileChooser fileChooser = fileConfig.getFileChooser();
        File fileToSave = fileChooser.showSaveDialog(new Stage());
        if(fileToSave == null) {
            AlertDialog.showWarningDialog("No file was chosen","");
        } else {
            fileConfig.save(ConfigurationCollection.getConfigsArrayList(), fileToSave, "Saving file...");
        }
    }

    /** Save all changes to the current opened file */

    @FXML
    void saveChanges() {
        fileConfig.saveChanges(ConfigurationCollection.getConfigsArrayList(), "Saving changes...");
    }

    /** Opens a popup window to show all components the user wants to compare */

    @FXML
    void compare(){
        ObservableList<PCComponents> selected = tableViewComponents.getSelectionModel().getSelectedItems();
        if(selected.isEmpty()) {
            AlertDialog.showWarningDialog("Choose components to compare","");
        } else if(selected.size() > 4){
            AlertDialog.showWarningDialog("You can only compare max 4 components","");
        } else if(selected.size() < 2){
            AlertDialog.showWarningDialog("You must choose at least 2 components", "");
        } else {
            PopupForComponents.compareComponent(selected);
        }
    }

    /** Opens a popup window and let's the user create their own pc configurations */

    @FXML
    void newConfiguration(){
        PopupForConfigurations.newConfiguration();
    }

    /** Opens a popup window to let the user filter the tableview */

    @FXML
    void filterTableView() {
        PopupForTableView.showFilterOptions(tableViewComponents);
    }

    /** searches through the tableview with the given search input */
    void search() {
        ComponentsCollection.collectionSearch(searchInput, tableViewComponents);
    }

    /* TODO: Prompt user when there are unsaved changes */

    @FXML
    void logout() throws IOException {
        ComponentsCollection.clearCollection();
        ComponentsCollection.setModified(false);
        App.setRoot("login");
    }
}
