package org.openjfx.controllers;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.PopupForComponents;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PopupNewConfigurationController implements Initializable {

    @FXML VBox vBoxConfigurationOptions;
    @FXML VBox vBoxConfigurations;
    @FXML TableView<PCComponents> tableViewComponents;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConfigurationCollection.itemCollectionOnChange(vBoxConfigurations);
        PopupForComponents.showComponentOnDoubleClick(tableViewComponents);
        tableViewComponents.setId("configurationsSelectTableView");
        HBox.setHgrow(vBoxConfigurations, Priority.ALWAYS);
        tableViewComponents.setItems(ComponentsCollection.getComponentObList());
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

    /** Shows the correct items on tableview depending on which button was click on the configuration options */
    public void fillTableView(Button button){
        FilteredList<PCComponents> filteredList = new FilteredList<>(ComponentsCollection.getComponentObList());
        Predicate<PCComponents> matchesType = c -> c.getComponentType().equals(button.getText());
        filteredList.setPredicate(matchesType);
        tableViewComponents.setItems(filteredList);
        tableViewComponents.refresh();
    }
}
