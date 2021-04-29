package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.AlertDialog;

public class ConfigurationCollection {

    private static final ObservableList<PCConfigurations> configObsList = FXCollections.observableArrayList();
    private static final ObservableList<PCComponents> itemsObsList = FXCollections.observableArrayList();

    /** Adds new item on the item obs list */
    public static void addConfigurationItem(PCComponents toAdd){
        if(itemsObsList.isEmpty()){
            itemsObsList.add(toAdd);
        } else if(itemsObsList.contains(toAdd)) {
            AlertDialog.showWarningDialog(toAdd.getComponentName() + " already selected!","");
        } else {
            boolean itemRemoved = itemsObsList.removeIf(p -> p.getComponentType().equals(toAdd.getComponentType()));
            if(itemRemoved) AlertDialog.showSuccessDialog(toAdd.getComponentType() + " has been replaced");
            itemsObsList.add(toAdd);
        }
    }

    /** Listens to changes and updates the list of selected components */
    public static void itemCollectionOnChange(VBox vBox){
        itemsObsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                vBox.getChildren().clear();
                for(PCComponents component: itemsObsList){
                    HBox wrapper = new HBox();
                    Label type = new Label(component.getComponentType() + ": ");
                    Label name = new Label(component.getComponentName() + "\t");
                    Label price = new Label(Double.toString(component.getComponentPrice()));
                    wrapper.setSpacing(10);
                    wrapper.getChildren().add(type);
                    wrapper.getChildren().add(name);
                    wrapper.getChildren().add(price);
                    wrapper.setStyle("-fx-background-color: gold; -fx-padding: 10px 10px 10px 10px");
                    vBox.getChildren().add(wrapper);
                }
            }
        });
    }

    /** Get / Set methods */
    public static ObservableList<PCComponents> getItemsObsList(){
        return itemsObsList;
    }
}
