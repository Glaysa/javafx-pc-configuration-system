package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import org.openjfx.dataModels.PCConfigurations;

public class ConfigurationCollection {

    private static ObservableList<PCConfigurations> configObsList = FXCollections.observableArrayList();

    /** Adds new components to the observable list componentObsList */
    public static void addToCollection(PCConfigurations pcConfiguration){
        configObsList.add(pcConfiguration);
    }

    /** Sets the items of the tableview to componentObsList */
    public static void setTableView(TableView<PCConfigurations> tableView){
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(configObsList);
    }

    /** Get / Set methods */
    public static ObservableList<PCConfigurations> getConfigObsList(){
        return configObsList;
    }
}
