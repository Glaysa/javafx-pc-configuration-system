package org.openjfx.controllers;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.PopupForComponents;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PopupNewConfigurationController implements Initializable {

    @FXML VBox vBoxConfigurationOptions;
    @FXML VBox vBoxConfigurations;
    @FXML TableView<PCComponents> tableViewComponents;
    private ArrayList<PCComponents> chosenConfigurations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PopupForComponents.showComponentOnDoubleClick(tableViewComponents);
        tableViewComponents.setId("configurationsSelectTableView");
    }

    public void loadConfigurationOptions(){
        for(String type: ComponentsCollection.getComponentTypeObsList()){
            Button button = new Button(type);
            button.setPrefWidth(400);
            button.setStyle("-fx-font-size: 14px");
            vBoxConfigurationOptions.getChildren().add(button);
            button.setOnAction((e) -> setButtonAction(button));
        }
    }

    public void setButtonAction(Button button){
        FilteredList<PCComponents> filteredList = new FilteredList<>(ComponentsCollection.getComponentObList());
        Predicate<PCComponents> matchesType = c -> c.getComponentType().equals(button.getText());
        filteredList.setPredicate(matchesType);
        tableViewComponents.setItems(filteredList);
        tableViewComponents.refresh();
    }
}
