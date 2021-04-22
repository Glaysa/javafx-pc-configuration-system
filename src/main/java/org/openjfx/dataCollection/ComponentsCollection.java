package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import org.openjfx.dataModels.PCComponents;
import java.util.ArrayList;

/** This class is responsible of all methods related to the components tableview */

public class ComponentsCollection {

    private static ObservableList<PCComponents> componentObsList = FXCollections.observableArrayList();
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

    /** This fills the combobox of component types with the defined types and new types found in the componentObsList */
    public static void fillCombobox_TYPE(ComboBox<String> typeOptions){
        String[] definedTypes = {"RAM","Keyboards","Processors","Graphic Cards", "Mouse"};
        componentTypeObsList = FXCollections.observableArrayList(definedTypes);
        for(PCComponents c : componentObsList){
            if(!componentTypeObsList.contains(c.getComponentType())){
                componentTypeObsList.add(c.getComponentType());
            }
        }
        typeOptions.setEditable(true);
        typeOptions.setPromptText("Choose/Enter Component Type");
        typeOptions.setItems(componentTypeObsList);
    }

    /** Updates the combobox of component types whenever there is a change in componentsObsList */
    public static void collectionOnChange(ComboBox<String> typeOptions){
        componentObsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                // Whenever there is a change on component list, update the combobox of component types
                fillCombobox_TYPE(typeOptions);
                // Keeps track of the obs list if its modified or not
                modified = true;
            }
        });
    }

    /** Get/Set methods */

    public static ArrayList<PCComponents> getComponentObsList() {
        return new ArrayList<>(componentObsList);
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
