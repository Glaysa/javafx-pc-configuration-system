package org.openjfx.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import java.net.URL;
import java.util.ResourceBundle;

public class PopupFilterTableViewController implements Initializable {

    @FXML private CheckBox checkProcessors;
    @FXML private CheckBox checkRam;
    @FXML private CheckBox checkKeyboards;
    @FXML private CheckBox checkMouse;
    @FXML private CheckBox checkGraphicCards;
    @FXML private CheckBox checkCabinet;
    @FXML private CheckBox checkOthers;
    @FXML private CheckBox checkLess500;
    @FXML private CheckBox checkLess1000;
    @FXML private CheckBox checkMore1000;
    private TableView<PCComponents> tableView;
    private final ObservableList<PCComponents> temp = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterByType(checkProcessors);
        filterByType(checkRam);
        filterByType(checkKeyboards);
        filterByType(checkMouse);
        filterByType(checkGraphicCards);
        filterByType(checkCabinet);
        filterByType(checkOthers);
    }

    @FXML
    void applyFilters() { }

    void filterByType(CheckBox checkBox){
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue){
                if(newValue.equals(true)) {
                    for(PCComponents component : ComponentsCollection.getComponentObList()){
                        if(component.getComponentType().equals(checkBox.getText())) {
                            if(!temp.contains(component)) temp.add(component);
                        }
                    }
                } else {
                    temp.removeIf(component -> component.getComponentType().equals(checkBox.getText()));
                }
                if(!temp.isEmpty()) tableView.setItems(temp);
                else tableView.setItems(ComponentsCollection.getComponentObList());
                tableView.refresh();
            }
        });
    }


    public void setTableView(TableView<PCComponents> tableView){
        this.tableView = tableView;
    }
}
