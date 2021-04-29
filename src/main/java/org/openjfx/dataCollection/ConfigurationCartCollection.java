package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.AlertDialog;

public class ConfigurationCartCollection {

    private static final ObservableList<PCConfigurations> cartObsList = FXCollections.observableArrayList();

    /** Adds new components to the observable list componentObsList */
    public static void addToCollection(PCConfigurations toAdd){
        if(cartObsList.contains(toAdd)) {
            String response = AlertDialog.showConfirmDialog(toAdd.getConfigurationName() + " is already in the cart. Add anyway?");
            if(response.equals("Yes")) cartObsList.add(toAdd);
        } else {
            cartObsList.add(toAdd);
        }
    }

    /** Sets the items of the tableview to componentObsList */
    public static void setTableView(TableView<PCConfigurations> tableView){
        tableView.setId("componentsCartTableView");
        tableView.setItems(cartObsList);
    }

    /** Updates the combobox of component types whenever there is a change in componentsObsList */
    public static void collectionOnChange(Label totalPrice){
        cartObsList.addListener(new ListChangeListener<PCConfigurations>() {
            @Override
            public void onChanged(Change<? extends PCConfigurations> change) {
                // Update total price
                double componentsTotalPrice = ComponentsCartCollection.getTotalPrice();
                double configurationTotalPrice = getTotalPrice();
                double totalSum = componentsTotalPrice + configurationTotalPrice;
                totalPrice.setText(Double.toString(totalSum));
            }
        });
    }

    /** Get / Set methods */

    public static double getTotalPrice(){
        double totalPrice = 0;
        for(PCConfigurations config : cartObsList) {
            totalPrice += config.getTotalPrice();
        }
        return totalPrice;
    }

}
