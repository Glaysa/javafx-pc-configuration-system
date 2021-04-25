package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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
    @FXML private Button clearAll;
    @FXML private Button selectAll;
    private final ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();
    private final ObservableList<PCComponents> temp = FXCollections.observableArrayList();
    private final ToggleGroup typeToggles = new ToggleGroup();
    private TableView<PCComponents> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize component type checkboxes
        checkBoxes.add(checkProcessors);
        checkBoxes.add(checkRam);
        checkBoxes.add(checkKeyboards);
        checkBoxes.add(checkMouse);
        checkBoxes.add(checkGraphicCards);
        checkBoxes.add(checkCabinet);
        checkBoxes.add(checkOthers);
        for(CheckBox checkBox: checkBoxes) checkBox.fire();

        // Price range choices
        checkAll.setToggleGroup(typeToggles);
        checkLess500.setToggleGroup(typeToggles);
        checkLess1000.setToggleGroup(typeToggles);
        checkMore1000.setToggleGroup(typeToggles);
        typeToggles.selectToggle(checkAll);

        // Filtering helpers
        selectAllHelper();
        clearAllHelper();
    }

    @FXML
    void applyFilters() {
        filter();
        if(!temp.isEmpty()) tableView.setItems(temp);
        tableView.refresh();
    }

    void filter(){
        temp.clear();
        for(CheckBox checkBox: checkBoxes){
            if(checkBox.isSelected()) {
                checkPriceRange(checkBox);
            }
        }
    }

    void checkPriceRange(CheckBox checkBox){
        FilteredList<PCComponents> filteredList = new FilteredList<>(ComponentsCollection.getComponentObList());

        // Conditions
        Predicate<PCComponents> matchedTypes = c -> c.getComponentType().equals(checkBox.getText());
        Predicate<PCComponents> otherTypes = c -> !c.getComponentType().equals(checkBox.getText());
        Predicate<PCComponents> priceLessThan500 = c -> c.getComponentPrice() < 500;
        Predicate<PCComponents> priceLessThan1000 = c -> c.getComponentPrice() < 1000;
        Predicate<PCComponents> priceMoreThan1000 = c -> c.getComponentPrice() > 1000;

        // Filters
        Predicate<PCComponents> filter1 = priceLessThan500.and(matchedTypes);
        Predicate<PCComponents> filter2 = priceLessThan1000.and(matchedTypes);
        Predicate<PCComponents> filter3 = priceMoreThan1000.and(matchedTypes);
        Predicate<PCComponents> filter4 = priceMoreThan1000.and(otherTypes);

        // Filter assignments
        if(typeToggles.getSelectedToggle().equals(checkAll)){
            filteredList.setPredicate(matchedTypes);
        } else if(typeToggles.getSelectedToggle().equals(checkLess500)){
            filteredList.setPredicate(filter1);
        } else if(typeToggles.getSelectedToggle().equals(checkLess1000)){
            filteredList.setPredicate(filter2);
        } else if(typeToggles.getSelectedToggle().equals(checkMore1000)){
            filteredList.setPredicate(filter3);
            filteredList.setPredicate(filter4);
        }

        // Apply filter to table
        temp.addAll(filteredList);
        tableView.setItems(temp);
    }

    void selectAllHelper(){
        selectAll.setOnAction((actionEvent -> {
            for(CheckBox c: checkBoxes) c.setSelected(true);
        }));
    }

    void clearAllHelper(){
        clearAll.setOnAction((actionEvent -> {
            for(CheckBox c: checkBoxes) c.setSelected(false);
        }));
    }

    public void setTableView(TableView<PCComponents> tableView){
        this.tableView = tableView;
    }
}
