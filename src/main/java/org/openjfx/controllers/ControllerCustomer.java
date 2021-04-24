package org.openjfx.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.openjfx.App;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.guiUtilities.AlertDialog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {

    @FXML private Label totalPriceLabel;
    @FXML private TextField searchInput;
    @FXML private TableView<PCComponents> tableViewCart;
    @FXML private TableView<PCComponents> tableViewProducts;
    private final FileActions<PCComponents> file = new FileActions<>();
    private final File defaultData = new File("src/main/java/database/initialComponents.txt");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Opens a file containing the default list of components.
        file.open(defaultData, "Loading products...");
        // (listener) Initializes detection of a change on the cart collection
        ComponentsCartCollection.collectionOnChange(totalPriceLabel);
        // Initializes the tableviews
        ComponentsCollection.setTableView(tableViewProducts);
        ComponentsCartCollection.setTableView(tableViewCart);
    }

    @FXML
    void addToCart() {
        ObservableList<PCComponents> selected = tableViewProducts.getSelectionModel().getSelectedItems();
        if(!selected.isEmpty()) {
            for(PCComponents toAdd : selected){
                if(ComponentsCartCollection.getComponentObsList().contains(toAdd)) {
                    String response = AlertDialog.showConfirmDialog(toAdd.getComponentName() + " is already in the cart, do you want to add another one?");
                    if(response.equals("Yes")) ComponentsCartCollection.addToCollection(toAdd);
                } else {
                    ComponentsCartCollection.addToCollection(toAdd);
                }
            }
        } else {
            AlertDialog.showWarningDialog("Please select a product to add from the products table","");
        }
        tableViewCart.refresh();
    }

    @FXML
    void removeFromCart() {
        PCComponents selected = tableViewCart.getSelectionModel().getSelectedItem();
        if(selected != null) {
            ComponentsCartCollection.removeSelected(selected);
        } else {
            AlertDialog.showWarningDialog("Please select a product to remove from the cart", "");
        }
        tableViewCart.refresh();
    }

    @FXML
    void checkout() {

    }

    @FXML
    void filterTableView() {

    }

    @FXML
    void logout() throws IOException {
        ComponentsCartCollection.clearCollection();
        App.setRoot("login");
    }

    @FXML
    void openFile() {

    }

    @FXML
    void saveChanges() {

    }

    @FXML
    void saveFile() {

    }
}
