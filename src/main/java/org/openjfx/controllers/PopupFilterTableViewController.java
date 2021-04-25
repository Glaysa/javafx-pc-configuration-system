package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML private RadioButton checkLess500;
    @FXML private RadioButton checkLess1000;
    @FXML private RadioButton checkMore1000;
    @FXML private RadioButton checkAll;
    private final ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();
    private final ObservableList<PCComponents> temp = FXCollections.observableArrayList();
    private final ToggleGroup group = new ToggleGroup();
    private TableView<PCComponents> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkBoxes.add(checkProcessors);
        checkBoxes.add(checkRam);
        checkBoxes.add(checkKeyboards);
        checkBoxes.add(checkMouse);
        checkBoxes.add(checkGraphicCards);
        checkBoxes.add(checkCabinet);
        checkBoxes.add(checkOthers);
        for(CheckBox checkBox: checkBoxes) checkBox.fire();

        checkAll.setToggleGroup(group);
        checkLess500.setToggleGroup(group);
        checkLess1000.setToggleGroup(group);
        checkMore1000.setToggleGroup(group);
        group.selectToggle(checkAll);
    }

    @FXML
    void applyFilters() {
        filter();
        if(!temp.isEmpty()) {
            tableView.setItems(temp);
        } else {
            for(PCComponents component : ComponentsCollection.getComponentObList()){
                checkPriceRange(component);
            }
        }
        tableView.sort();
        tableView.refresh();
    }

    void filter(){
        temp.clear();
        for(CheckBox checkBox : checkBoxes){
            if(checkBox.isSelected()){
                for(PCComponents component : ComponentsCollection.getComponentObList()){
                    if(component.getComponentType().equals(checkBox.getText())){
                        checkPriceRange(component);
                    }
                }
            } else {
                temp.removeIf(component -> component.getComponentType().equals(checkBox.getText()));
            }
        }
    }

    void checkPriceRange(PCComponents component){
        if(group.getSelectedToggle().equals(checkAll)){
            temp.add(component);
        } else if(group.getSelectedToggle().equals(checkLess500)){
            if(component.getComponentPrice() < 500){
                temp.add(component);
            }
        } else if(group.getSelectedToggle().equals(checkLess1000)){
            if(component.getComponentPrice() < 1000){
                temp.add(component);
            }
        }
    }

    public void setTableView(TableView<PCComponents> tableView){
        this.tableView = tableView;
    }
}
