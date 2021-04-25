package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PopupFilterTableViewController implements Initializable {

    @FXML private VBox vBox;
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

    /** Applies all filters */

    @FXML
    void applyFilters() {
        temp.clear();
        for(CheckBox checkBox: checkBoxes){
            if(checkBox.isSelected()) {
                filter(checkBox);
            }
        }
        if(!temp.isEmpty()) tableView.setItems(temp);
        tableView.refresh();
    }

    /** Filters the tableview based on ticked checkboxes */
    void filter(CheckBox checkBox){
        FilteredList<PCComponents> filteredList = new FilteredList<>(ComponentsCollection.getComponentObList());

        // Conditions
        Predicate<PCComponents> matchedTypes = c -> c.getComponentType().equals(checkBox.getText());
        Predicate<PCComponents> priceLessThan500 = c -> c.getComponentPrice() < 500;
        Predicate<PCComponents> priceLessThan1000 = c -> c.getComponentPrice() < 1000;
        Predicate<PCComponents> priceMoreThan1000 = c -> c.getComponentPrice() > 1000;

        // Filters
        Predicate<PCComponents> filter1 = priceLessThan500.and(matchedTypes);
        Predicate<PCComponents> filter2 = priceLessThan1000.and(matchedTypes);
        Predicate<PCComponents> filter3 = priceMoreThan1000.and(matchedTypes);

        // Filter assignments
        if(typeToggles.getSelectedToggle().equals(checkAll)){
            filteredList.setPredicate(matchedTypes);
        } else if(typeToggles.getSelectedToggle().equals(checkLess500)){
            filteredList.setPredicate(filter1);
        } else if(typeToggles.getSelectedToggle().equals(checkLess1000)){
            filteredList.setPredicate(filter2);
        } else if(typeToggles.getSelectedToggle().equals(checkMore1000)){
            filteredList.setPredicate(filter3);
        }

        // Apply filter to table
        temp.addAll(filteredList);
        tableView.setItems(temp);
    }

    /** Checks all checkboxes */
    void selectAllHelper(){
        selectAll.setOnAction((actionEvent -> {
            for(CheckBox c: checkBoxes) c.setSelected(true);
        }));
    }

    /** Unchecks all checkboxes */
    void clearAllHelper(){
        clearAll.setOnAction((actionEvent -> {
            for(CheckBox c: checkBoxes) c.setSelected(false);
        }));
    }

    /** Creates the filter checkboxes */
    public void createFilterCheckboxes(){
        for(PCComponents object: ComponentsCollection.getComponentObList()) {
            CheckBox checkBox = new CheckBox(object.getComponentType());
            vBox.getChildren().add(checkBox);
            if(!checkBoxes.contains(checkBox)) checkBoxes.add(checkBox);
        }
    }

    /** Gets access to tableview */
    public void setTableView(TableView<PCComponents> tableView){
        this.tableView = tableView;
    }
}
