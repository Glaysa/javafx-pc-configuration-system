package org.openjfx.data_collection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.openjfx.data_models.PCComponents;
import java.util.ArrayList;

public class ComponentsCollection {

    private static ObservableList<PCComponents> obsList = FXCollections.observableArrayList();
    private static ObservableList<String> obsList_TYPES;

    public static void addToCollection(PCComponents toAdd){
        obsList.add(toAdd);
    }

    public static void setTableView(TableView<PCComponents> tableView){
        tableView.setItems(obsList);
    }

    public static void clearCollection(){
        obsList.clear();
    }

    // This fills the combobox of component types with the defined and new values of component types
    public static void fillCombobox_TYPE(ComboBox<String> typeOptions){
        String[] definedTypes = {"RAM","Keyboards","Processors","Graphic Cards", "Mouse"};
        ObservableList<String> types = FXCollections.observableArrayList(definedTypes);
        for(PCComponents c : obsList){
            if(!types.contains(c.getComponentType())){
                types.add(c.getComponentType());
            }
        }
        typeOptions.setEditable(true);
        typeOptions.setPromptText("Choose/Enter Component Type");
        typeOptions.setItems(types);
        obsList_TYPES = types;
    }

    // Updates the combobox of component types whenever there is a change on the observable list of components
    public static void listOnChanged(ComboBox<String> typeOptions){
        obsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                fillCombobox_TYPE(typeOptions);
            }
        });
    }

    public static void setObsList(ArrayList<PCComponents> data) {
        ComponentsCollection.obsList = FXCollections.observableArrayList(data);
    }

    public static ObservableList<PCComponents> getObsList() {
        return obsList;
    }
}
