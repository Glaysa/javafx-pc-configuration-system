package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.AlertDialog;
import java.util.ArrayList;
import java.util.Arrays;

/** This class is responsible of all methods related to the configuration tableview in the admin view */

public class ConfigurationCollection {

    private static final ObservableList<PCConfigurations> configObsList = FXCollections.observableArrayList();
    private static final ObservableList<PCComponents> itemsObsList = FXCollections.observableArrayList();

    /** Adds configured PC to configObsList */
    public static void addConfiguration(PCConfigurations toAdd){
        // Must have components when configuring a PC
        String[] mustHave = {"RAM", "Mouse", "Keyboards"};
        ArrayList<String> mustHaveComponents = new ArrayList<>(Arrays.asList(mustHave));

        // Removes type from mustHave[] if is selected
        for(PCComponents item: toAdd.getPcComponents()) {
            mustHaveComponents.removeIf(c -> c.equals(item.getComponentType()));
        }

        // When mustHave[] is empty, it means all component needed to configure a PC is added
        if(mustHaveComponents.isEmpty()){
            configObsList.add(toAdd);

        // Otherwise, the user is shown with the other components that is needed to be added
        } else {
            StringBuilder missing = new StringBuilder();
            for(String m: mustHaveComponents) {
                missing.append(m).append(", ");
            }
            throw new IllegalArgumentException("Missing components: " + missing);
        }
    }

    /** Adds selected component to itemObsList */
    public static void addConfigurationItem(PCComponents toAdd){
        // The user can only add one component of each type when configuring a PC
        if(itemsObsList.isEmpty()){
            itemsObsList.add(toAdd);
        } else if(itemsObsList.contains(toAdd)) {
            throw new IllegalArgumentException(toAdd.getComponentName() + " already selected!");
        } else {
            boolean itemRemoved = itemsObsList.removeIf(p -> p.getComponentType().equals(toAdd.getComponentType()));
            if(itemRemoved) AlertDialog.showSuccessDialog(toAdd.getComponentType() + " has been replaced");
            itemsObsList.add(toAdd);
        }
    }

    /** Sets the tableview */
    public static void setTableView(TableView<PCConfigurations> tableView){
        tableView.setId("configurationTableView");
        tableView.setItems(configObsList);
    }

    /** Listens to changes and updates the list of selected components */
    public static void itemCollectionOnChange(VBox vBox, Label totalPrice){
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
                totalPrice.setText(Double.toString(getTotalPrice()));
            }
        });
    }

    /** Clears collections */

    public static void clearCollection(){
        configObsList.clear();
    }

    public static void clearItemCollection(){
        itemsObsList.clear();
    }

    /** Calculates the total price of all the items added to configure a PC */
    public static double getTotalPrice(){
        double totalPrice = 0;
        for(PCComponents component : itemsObsList) {
            totalPrice += component.getComponentPrice();
        }
        return totalPrice;
    }

    /** Get / Set methods */

    public static ArrayList<PCComponents> getItemsArrayList(){
        return new ArrayList<>(itemsObsList);
    }

    public static ArrayList<PCConfigurations> getConfigsArrayList(){
        return new ArrayList<>(configObsList);
    }
}
