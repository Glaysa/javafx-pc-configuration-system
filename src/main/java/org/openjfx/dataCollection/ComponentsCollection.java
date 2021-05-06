package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.Indicators;
import java.util.ArrayList;
import java.util.function.Predicate;

/** This class is responsible of all methods related to the components tableview in the admin view and
 * in the customer view */

public class ComponentsCollection {

    private static final ObservableList<PCComponents> componentObsList = FXCollections.observableArrayList();
    private static ObservableList<String> componentTypeObsList;
    private static boolean modified = false;

    /** Adds new components to the observable list componentObsList */
    public static void addToCollection(PCComponents toAdd){
        componentObsList.add(toAdd);
    }

    /** Sets the items of the tableview to componentObsList */
    public static void setTableView(TableView<PCComponents> tableView){
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setId("componentsTableView");
        tableView.setItems(componentObsList);
    }

    /** Clears the componentObsList */
    public static void clearCollection(){
        componentObsList.clear();
    }

    /** Removes all selected values from the collection */
    public static void removeAllSelected(ObservableList<PCComponents> toRemove){
        componentObsList.removeAll(toRemove);
    }

    /** Fills a combobox with the componentTypeObsList items */
    public static void fillComponentTypeComboBox(ComboBox<String> typeOptions){
        typeOptions.setEditable(true);
        typeOptions.setPromptText("Choose/Enter Component Type");
        typeOptions.setItems(componentTypeObsList);
    }

    /** This fills the componentTypeObslist with the defined types and new types found in the componentObsList */
    public static void fillComponentTypeObsList(){
        String[] definedTypes = {"CPU","Cooler","Motherboard","RAM", "Storage","Case","Power Supply", "Operating System", "Monitor", "External Storage"};
        componentTypeObsList = FXCollections.observableArrayList(definedTypes);
        for(PCComponents c : componentObsList){
            if(!componentTypeObsList.contains(c.getComponentType())){
                componentTypeObsList.add(c.getComponentType());
            }
        }
    }

    /** Updates certain variables whenever there is a change in componentsObsList */
    public static void collectionOnChange(ComboBox<String> typeOptions) {
        componentObsList.addListener((ListChangeListener<PCComponents>) change -> {
            try {
                modified = true;
                fillComponentTypeObsList();
                fillComponentTypeComboBox(typeOptions);
                Indicators.updateFileStatusAtAdmin(true);
            } catch (NullPointerException ignore){}
        });
    }

    /** Searches through the componentObsList */
    public static void collectionSearch(TextField searchInput, TableView<PCComponents> tableView){
        FilteredList<PCComponents> filteredList = new FilteredList<>(componentObsList);
        searchInput.textProperty().addListener((observable, oldValue, newValue)-> {
            Predicate<PCComponents> matchesName = p -> p.getComponentName().toLowerCase().contains(newValue);
            Predicate<PCComponents> matchesType = p -> p.getComponentType().toLowerCase().contains(newValue);
            Predicate<PCComponents> matchesID = p -> Integer.toString(p.getPCComponentID()).equals(newValue);
            Predicate<PCComponents> matching = matchesName.or(matchesType).or(matchesID);

            if(newValue.isEmpty()) {
                filteredList.setPredicate(null);
            } else {
                filteredList.setPredicate(matching);
            }
            tableView.setItems(filteredList);
        });
    }

    /** Get/Set methods */

    public static ArrayList<Object> getComponentArrayList() {
        return new ArrayList<>(componentObsList);
    }

    public static ObservableList<PCComponents> getComponentObList() {
        return componentObsList;
    }

    public static ObservableList<String> getComponentTypeObsList(){
        return componentTypeObsList;
    }

    public static boolean isModified() {
        return modified;
    }

    public static void setModified(boolean modified) {
        ComponentsCollection.modified = modified;
    }
}
