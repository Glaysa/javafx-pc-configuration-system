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

    private static ObservableList<PCComponents> obsList = FXCollections.observableArrayList();
    private static ObservableList<String> obsList_TYPES;

    /** Adds new items to observable list of components */
    public static void addToCollection(PCComponents toAdd){
        obsList.add(toAdd);
    }

    /** Sets the items of the tableview */
    public static void setTableView(TableView<PCComponents> tableView){
        tableView.setItems(obsList);
    }

    /** Clear the observable list of components */
    public static void clearCollection(){
        obsList.clear();
    }

    /** This fills the combobox of component types with the defined and new values found in the component observable list */
    public static void fillCombobox_TYPE(ComboBox<String> typeOptions){
        String[] definedTypes = {"RAM","Keyboards","Processors","Graphic Cards", "Mouse"};
        obsList_TYPES = FXCollections.observableArrayList(definedTypes);
        for(PCComponents c : obsList){
            if(!obsList_TYPES.contains(c.getComponentType())){
                obsList_TYPES.add(c.getComponentType());
            }
        }
        typeOptions.setEditable(true);
        typeOptions.setPromptText("Choose/Enter Component Type");
        typeOptions.setItems(obsList_TYPES);
    }

    /** Updates the combobox of component types whenever there is a change on the observable list of components
     *  including the combobox in the editable tableview */
    public static void fillCombobox_TYPE_listOnChanged(ComboBox<String> typeOptions, TableColumn<PCComponents, String> typeColumn){
        obsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                fillCombobox_TYPE(typeOptions);
                typeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(obsList_TYPES));
            }
        });
    }

    public static void setObsList(ArrayList<PCComponents> data) {
        ComponentsCollection.obsList = FXCollections.observableArrayList(data);
    }

    public static ObservableList<PCComponents> getObsList() {
        return obsList;
    }

    public static ObservableList<String> getObsList_TYPES(){
        return obsList_TYPES;
    }
}
