package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.AlertDialog;

/** This class is responsible of all methods related to the components cart tableview in the customer view */

public class ComponentsCartCollection {

    private static final ObservableList<PCComponents> cartObsList = FXCollections.observableArrayList();

    /** Adds new components to the observable list componentObsList */
    public static void addToCollection(PCComponents toAdd){
        if(cartObsList.contains(toAdd)) {
            String response = AlertDialog.showConfirmDialog(toAdd.getComponentName() + " is already in the cart. Add anyway?");
            if(response.equals("Yes")) cartObsList.add(toAdd);
        } else {
            cartObsList.add(toAdd);
        }
    }

    /** Sets the items of the tableview to componentObsList */
    public static void setTableView(TableView<PCComponents> tableView){
        tableView.setId("componentsCartTableView");
        tableView.setItems(cartObsList);
    }

    /** Removes all selected values from the collection */
    public static void removeSelected(PCComponents toRemove){
        if(toRemove != null) cartObsList.remove(toRemove);
        else AlertDialog.showWarningDialog("Please select a product to remove", "");
    }

    /** Updates the combobox of component types whenever there is a change in componentsObsList */
    public static void collectionOnChange(Label totalPrice){
        cartObsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                // Update total price
                double configurationTotalPrice = ConfigurationCartCollection.getTotalPrice();
                double componentsTotalPrice = getTotalPrice();
                double totalSum = componentsTotalPrice + configurationTotalPrice;
                totalPrice.setText(Double.toString(totalSum));
            }
        });
    }

    /** Get / Set methods */

    public static double getTotalPrice(){
        double totalPrice = 0;
        for(PCComponents component : cartObsList) {
            totalPrice += component.getComponentPrice();
        }
        return totalPrice;
    }
}
