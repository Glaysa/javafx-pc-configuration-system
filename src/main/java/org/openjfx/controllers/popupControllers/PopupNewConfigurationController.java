package org.openjfx.controllers.popupControllers;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.Indicators;
import org.openjfx.guiUtilities.popupDialogs.PopupForComponents;
import org.openjfx.guiUtilities.popupDialogs.PopupUtilities;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/** This controller is used for the popup window when creating new pc configurations */

public class PopupNewConfigurationController implements Initializable {

    @FXML private VBox parentPane;
    @FXML private VBox vBoxConfigurationOptions;
    @FXML private VBox vBoxConfigurations;
    @FXML private TableView<PCComponents> tableViewComponents;
    @FXML private Label totalPrice;
    public static Label totalPriceStatic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Assign to another static element to give access to other classes
        totalPriceStatic = totalPrice;
        // Initializes tableview
        tableViewComponents.setId("configurationsSelectTableView");
        tableViewComponents.setItems(ComponentsCollection.getComponentObList());
        // Initializes collection change listener
        ConfigurationCollection.itemCollectionOnChange(vBoxConfigurations, totalPriceStatic);
        // Initializes detection of double click on row of tableview
        PopupForComponents.showComponentDetails(tableViewComponents);
        // Layout Initialization
        HBox.setHgrow(vBoxConfigurations, Priority.ALWAYS);
        // Initializes tableview tooltip
        Indicators.showToolTip(tableViewComponents, "Double click to see component details");
    }

    /** Adds new configuration to a table view */

    @FXML
    void addConfiguration(){
        try {
            int id = PCConfigurations.createID();
            ArrayList<PCComponents> list = ConfigurationCollection.getItemsArrayList();
            double totalPrice = ConfigurationCollection.getTotalPrice();
            PCConfigurations configuration = new PCConfigurations("Configuration " + id, id, list, totalPrice);
            ConfigurationCollection.addConfiguration(configuration);
            ConfigurationCollection.clearItemCollection();
            PopupUtilities.closePopup(parentPane);
        } catch (Exception e) {
            AlertDialog.showWarningDialog(e.getMessage(),"");
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** Let user save the configuration they made */

    @FXML
    void saveConfiguration(){

        // try/catch -> if exception in addConfiguration(), the following code blocks will not be executed
        // If try/block is removed, a file chooser will still open which is unnecessary when an exception occurs

        try {

            // Add the configured PC in the collection
            addConfiguration();

            // Take the last added configured PC and add it on an arraylist
            int index = ConfigurationCollection.getConfigObsList().size() - 1;
            ArrayList<Object> singleConfigToSave = new ArrayList<>();
            singleConfigToSave.add(ConfigurationCollection.getConfigObsList().get(index));

            // Open a file chooser and save the arraylist
            FileActions file = new FileActions();
            FileChooser fileChooser = file.getFileChooser();
            File fileToSave = fileChooser.showSaveDialog(new Stage());
            if(fileToSave == null) {
                AlertDialog.showWarningDialog("No file was chosen","");
            } else {
                file.save(singleConfigToSave, fileToSave, "Saving Configuration...");
            }

        // Exception ignored because already shown in addConfiguration() with an alert dialog
        } catch (Exception ignored){}
    }

    /** Create buttons as configuration options based on all component types */
    public void loadConfigurationOptions(){
        for(String type: ComponentsCollection.getComponentTypeObsList()){
            Button button = new Button(type);
            button.setPrefWidth(400);
            button.setStyle("-fx-font-size: 14px");
            vBoxConfigurationOptions.getChildren().add(button);
            button.setOnAction((e) -> fillTableView(button));
        }
    }

    /** Shows the correct items on tableview depending on which configuration button
     * was clicked on the configuration options */
    public void fillTableView(Button button) {
        FilteredList<PCComponents> filteredList = new FilteredList<>(ComponentsCollection.getComponentObList());
        Predicate<PCComponents> matchesType = c -> c.getComponentType().equals(button.getText());
        filteredList.setPredicate(matchesType);
        tableViewComponents.setItems(filteredList);
        tableViewComponents.refresh();
    }
}
