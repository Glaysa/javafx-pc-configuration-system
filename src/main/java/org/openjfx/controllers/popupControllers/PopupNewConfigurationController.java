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
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.popupDialogs.PopupForComponents;
import org.openjfx.guiUtilities.popupDialogs.PopupUtilities;
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
        PopupForComponents.showComponentOnDoubleClick(tableViewComponents);
        // Layout Initialization
        HBox.setHgrow(vBoxConfigurations, Priority.ALWAYS);
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
}
