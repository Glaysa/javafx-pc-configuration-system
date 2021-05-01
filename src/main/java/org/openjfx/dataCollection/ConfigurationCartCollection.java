package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.AlertDialog;

/** This class is responsible of all methods related to the configuration cart tableview in the customer view */

public class ConfigurationCartCollection {

    private static final ObservableList<PCConfigurations> cartObsList = FXCollections.observableArrayList();

    /** Adds new configured PC to the observable list cartObsList */
    public static void addToCollection(PCConfigurations toAdd){
        if(cartObsList.contains(toAdd)) {
            String response = AlertDialog.showConfirmDialog(toAdd.getConfigurationName() + " is already in the cart. Add anyway?");
            if(response.equals("Yes")) {
                cartObsList.add(toAdd);
                AlertDialog.showSuccessDialog(toAdd.getConfigurationName() + " is added to your cart!");
            }
        } else {
            cartObsList.add(toAdd);
            AlertDialog.showSuccessDialog(toAdd.getConfigurationName() + " is added to your cart!");
        }
    }

    /** Sets the items of the tableview to cartObsList */
    public static void setTableView(TableView<PCConfigurations> tableView){
        tableView.setId("configurationCartTableView");
        tableView.setItems(cartObsList);
    }

    /** Removes all selected values from cartObsList */
    public static void removeSelected(PCConfigurations toRemove){
        if(toRemove != null) cartObsList.remove(toRemove);
        else AlertDialog.showWarningDialog("Please select a configured PC to remove", "");
    }

    /** Updates the total price whenever there is change on all carts */
    public static void collectionOnChange(Label totalPrice){
        cartObsList.addListener(new ListChangeListener<PCConfigurations>() {
            @Override
            public void onChanged(Change<? extends PCConfigurations> change) {
                double componentsTotalPrice = ComponentsCartCollection.getTotalPrice();
                double configurationTotalPrice = getTotalPrice();
                double totalSum = componentsTotalPrice + configurationTotalPrice;
                totalPrice.setText(Double.toString(totalSum));
            }
        });
    }

    /** Calculates the total price of all configured PC in the cart */
    public static double getTotalPrice(){
        double totalPrice = 0;
        for(PCConfigurations config : cartObsList) {
            totalPrice += config.getTotalPrice();
        }
        return totalPrice;
    }
}
