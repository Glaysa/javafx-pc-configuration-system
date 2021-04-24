package org.openjfx.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.openjfx.dataModels.PCComponents;
import java.util.ArrayList;

/** This class is responsible of all methods related to the components cart tableview in the customer view */

public class ComponentsCartCollection {

    private static final ObservableList<PCComponents> cartObsList = FXCollections.observableArrayList();
    private static boolean modified = false;

    /** Adds new components to the observable list componentObsList */
    public static void addToCollection(PCComponents toAdd){
        cartObsList.add(toAdd);
    }

    /** Sets the items of the tableview to componentObsList */
    public static void setTableView(TableView<PCComponents> tableView){
        tableView.setItems(cartObsList);
    }

    /** Clears the componentObsList */
    public static void clearCollection(){
        cartObsList.clear();
    }

    /** Removes all selected values from the collection */
    public static void removeSelected(PCComponents toRemove){
        cartObsList.remove(toRemove);
    }

    /** Updates the combobox of component types whenever there is a change in componentsObsList */
    public static void collectionOnChange(Label totalPrice){
        cartObsList.addListener(new ListChangeListener<PCComponents>() {
            @Override
            public void onChanged(Change<? extends PCComponents> change) {
                // Keeps track of the obs list if its modified or not
                modified = true;
                // Update total price
                totalPrice.setText(Double.toString(getTotalPrice()));
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

    public static ArrayList<PCComponents> getComponentObsList() {
        return new ArrayList<>(cartObsList);
    }

    public static boolean isModified() {
        return modified;
    }

    public static void setModified(boolean modified) {
        ComponentsCartCollection.modified = modified;
    }
}
