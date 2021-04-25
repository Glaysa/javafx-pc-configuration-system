package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.Indicators;
import java.util.ArrayList;

/** This class is responsible of all methods related to the components tableview in the admin view and
 * products tableview in customer view */

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

    /** Fills a combobox with the type obs list items */
    public static void fillComponentTypeComboBox(ComboBox<String> typeOptions){
        typeOptions.setEditable(true);
        typeOptions.setPromptText("Choose/Enter Component Type");
        typeOptions.setItems(componentTypeObsList);
    }

    /** This fills the combobox of component types with the defined types and new types found in the componentObsList */
    public static void fillComponentTypeObsList(){
        String[] definedTypes = {"RAM","Keyboards","Processors","Graphic Cards", "Mouse"};
        componentTypeObsList = FXCollections.observableArrayList(definedTypes);
        for(PCComponents c : componentObsList){
            if(!componentTypeObsList.contains(c.getComponentType())){
                componentTypeObsList.add(c.getComponentType());
            }
        }
    }

    /** Updates the combobox of component types whenever there is a change in componentsObsList */
    public static void collectionOnChange(ComboBox<String> typeOptions) {
        componentObsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                try {
                    // Checks if there are new component type, if yes, it adds it to the combobox given
                    fillComponentTypeObsList();
                    // Update the combobox of component types
                    fillComponentTypeComboBox(typeOptions);
                    // Keeps track of the obs list if its modified or not
                    modified = true;
                    // Shows that the file is modified
                    Indicators.updateFileStatus(true);
                } catch (NullPointerException ignore){}
            }
        });
    }

    /** Get/Set methods */

    public static ArrayList<PCComponents> getComponentArrayList() {
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
