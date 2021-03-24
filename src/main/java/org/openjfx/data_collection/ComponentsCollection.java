package org.openjfx.data_collection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import org.openjfx.data_models.PCComponents;
import java.util.ArrayList;

public class ComponentsCollection {

    private static ObservableList<PCComponents> componentObsList = FXCollections.observableArrayList();
    private static ObservableList<String> componentTypeObsList;

    /** Adds new components to the observable list componentObsList */

    public static void addToCollection(PCComponents toAdd){
        componentObsList.add(toAdd);
    }

    /** Sets the items of the tableview to componentObsList */

    public static void setTableView(TableView<PCComponents> tableView){
        tableView.setItems(componentObsList);
    }

    /** Clears the componentObsList */

    public static void clearCollection(){
        componentObsList.clear();
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

    /** Updates the combobox of component types whenever there is a change in componentsObsList
        including the combobox of typeColumn in the editable tableview */

    public static void fillCombobox_TYPE_listOnChanged(ComboBox<String> typeOptions, TableColumn<PCComponents, String> typeColumn){
        componentObsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                fillCombobox_TYPE(typeOptions);
                typeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(componentTypeObsList));
            }
        });
    }

    public static void setComponentObsList(ArrayList<PCComponents> data) {
        ComponentsCollection.componentObsList = FXCollections.observableArrayList(data);
    }

    public static ObservableList<PCComponents> getComponentObsList() {
        return componentObsList;
    }

    public static ObservableList<String> getComponentTypeObsList(){
        return componentTypeObsList;
    }
}
