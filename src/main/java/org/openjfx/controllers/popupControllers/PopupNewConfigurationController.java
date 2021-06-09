package org.openjfx.controllers.popupControllers;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.dataValidator.ConfigurationValidator;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.fileUtilities.FileParser;
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
    @FXML private TextField configName;
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
        Indicators.showToolTip(tableViewComponents, "Double click to see component details\nDrag to add to component list");
        // (listener) tableview on drag
        tvOnDragDetected();
        vboxOnDrag();
    }

    /** Adds new configuration to a table view */

    @FXML
    void addConfiguration(){
        try {

            // Properties needed to instantiate a configuration object
            int id = PCConfigurations.createID();
            ArrayList<PCComponents> list = ConfigurationCollection.getItemsArrayList();
            double totalPrice = ConfigurationCollection.getTotalPrice();
            if(configName.getText().equals("")) configName.setText("Configuration " + id);

            // Instantiation of config object
            PCConfigurations configuration = new PCConfigurations(configName.getText(), id, list, totalPrice);

            // Config object is added to the tv and the popup is closed
            ConfigurationCollection.addConfiguration(configuration); // validation occurs here
            ConfigurationCollection.clearItemCollection();
            PopupUtilities.closePopup(parentPane);

        } catch (Exception e) {
            AlertDialog.showWarningDialog(e.getMessage(),"");
        }
    }

    /** Let user save the configuration they made */

    @FXML
    void saveConfiguration(){

        try {

            // Properties needed to instantiate a configuration object
            int id = PCConfigurations.createID();
            ArrayList<PCComponents> list = ConfigurationCollection.getItemsArrayList();
            double totalPrice = ConfigurationCollection.getTotalPrice();
            if(configName.getText().equals("")) configName.setText("Configuration " + id);

            // Instantiation of config object
            PCConfigurations configuration = new PCConfigurations(configName.getText(), id, list, totalPrice);

            // Validates config object before saving
            ConfigurationValidator.validateConfiguredPC(configuration);

            // Save operation only accepts arraylist therefore must be declared here even though it only contains 1 element
            ArrayList<Object> singleConfigToSave = new ArrayList<>();
            singleConfigToSave.add(configuration);

            // Open a file chooser and saves the arraylist
            FileActions file = new FileActions();
            FileChooser fileChooser = file.getFileChooser();
            fileChooser.setInitialFileName(configName.getText());
            File fileToSave = fileChooser.showSaveDialog(new Stage());
            if(fileToSave == null) {
                AlertDialog.showWarningDialog("No file was chosen","");
            } else {
                file.save(singleConfigToSave, fileToSave, "Saving Configuration...");
                addConfiguration();
            }

        } catch (Exception e){
            AlertDialog.showWarningDialog(e.getMessage(),"");
        }
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

    /** Let's user drag item from tv to vbox to add component to config pc */
    void tvOnDragDetected(){
        tableViewComponents.setOnDragDetected((event) -> {
            Dragboard db = tableViewComponents.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cc = new ClipboardContent();
            cc.putString(tableViewComponents.getSelectionModel().getSelectedItem().toString());
            db.setContent(cc);
            event.consume();
        });
    }

    /** Accept dragged item from tv */
    void vboxOnDrag(){
        // Enable reception
        vBoxConfigurations.setOnDragOver((event) -> {
            event.acceptTransferModes(TransferMode.ANY);
        });
        // Process received data
        vBoxConfigurations.setOnDragDropped((event) -> {
            String data = event.getDragboard().getString();
            PCComponents component = (PCComponents) FileParser.parseObject(data);
            ConfigurationCollection.addConfigurationItem(component);
        });
    }
}
