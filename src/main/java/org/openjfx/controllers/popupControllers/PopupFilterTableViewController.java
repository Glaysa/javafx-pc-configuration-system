package org.openjfx.controllers.popupControllers;

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
import java.util.*;
import java.util.function.Predicate;

/** This controller is used for the popup window of when user wants to filter the tableview */

public class PopupFilterTableViewController implements Initializable {

    @FXML private VBox vBox;
    @FXML private RadioButton checkLess500;
    @FXML private RadioButton checkLess1000;
    @FXML private RadioButton checkMore1000;
    @FXML private RadioButton checkAll;
    @FXML private Button clearAll;
    @FXML private Button selectAll;
    @FXML private Button reset;
    private final ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();
    private final ObservableList<PCComponents> temp = FXCollections.observableArrayList();
    private final ToggleGroup typeToggles = new ToggleGroup();
    private TableView<PCComponents> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Price range choices
        checkAll.setToggleGroup(typeToggles);
        checkLess500.setToggleGroup(typeToggles);
        checkLess1000.setToggleGroup(typeToggles);
        checkMore1000.setToggleGroup(typeToggles);
        typeToggles.selectToggle(checkAll);

        // Initializes Filtering helpers
        selectAll();
        clearAll();
        resetFilter();
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
    }

    /** Filters the tableview based on which checkboxes are ticked */
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
        tableView.refresh();
    }

    /** Checks all checkboxes */
    void selectAll(){
        selectAll.setOnAction((actionEvent -> {
            for(CheckBox c: checkBoxes) c.setSelected(true);
        }));
    }

    /** Unchecks all checkboxes */
    void clearAll(){
        clearAll.setOnAction((actionEvent -> {
            for(CheckBox c: checkBoxes) c.setSelected(false);
        }));
    }

    /** Resets all filters and tableview to their original values */
    void resetFilter(){
        reset.setOnAction(actionEvent -> {
            tableView.setItems(ComponentsCollection.getComponentObList());
            selectAll.fire();
            typeToggles.selectToggle(checkAll);
        });
    }

    /** Creates the filter checkboxes */
    public void createFilterCheckboxes(){
        for(String type: ComponentsCollection.getComponentTypeObsList()) {
            CheckBox checkBox = new CheckBox(type);
            vBox.getChildren().add(checkBox);
            checkBoxes.add(checkBox);
            checkBox.fire();
        }
    }

    /** Gets access to tableview */
    public void setTableView(TableView<PCComponents> tableView){
        this.tableView = tableView;
    }
}
