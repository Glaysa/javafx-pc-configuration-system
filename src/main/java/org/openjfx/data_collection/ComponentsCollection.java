package org.openjfx.data_collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.openjfx.data_models.PCComponents;
import java.util.ArrayList;

public class ComponentsCollection {

    private static ObservableList<PCComponents> obsList = FXCollections.observableArrayList();

    public static void addToCollection(PCComponents toAdd){
        obsList.add(toAdd);
    }

    public static void addToCollection(ArrayList<PCComponents> toAdd){
        for(PCComponents component : toAdd){
            addToCollection(component);
        }
    }

    public static void setTableView(TableView<PCComponents> tableView){
        tableView.setItems(obsList);
    }

    public static void setObsList(ArrayList<PCComponents> data) {
        ComponentsCollection.obsList = FXCollections.observableArrayList(data);
    }

    public static ObservableList<PCComponents> getObsList() {
        return obsList;
    }
}
