package org.openjfx.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.openjfx.App;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.Indicators;
import org.openjfx.guiUtilities.PopupForComponents;
import org.openjfx.guiUtilities.PopupForTableView;
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
        // (listener)
        ComponentsCollection.collectionOnChange(null);
        // Fills component type obs list
        ComponentsCollection.fillComponentTypeObsList();
        // (listener) Initializes detection of double click on row of tableview
        showComponentOnDoubleClick(tableViewProducts);
        showComponentOnDoubleClick(tableViewCart);
        // Initializes tableview tooltips
        Indicators.showToolTip(tableViewProducts, "Double click to see product details");
        Indicators.showToolTip(tableViewCart, "Double click to see product details");
        // (listener) Initializes search functionality
        search();
    }

    /** Add components to cart */

    @FXML
    void addToCart() {
        ObservableList<PCComponents> selected = tableViewProducts.getSelectionModel().getSelectedItems();
        if(!selected.isEmpty()) for(PCComponents toAdd : selected) ComponentsCartCollection.addToCollection(toAdd);
        else AlertDialog.showWarningDialog("Please select a product to add","");
        tableViewCart.refresh();
    }

    /** Removes a component from the cart */

    @FXML
    void removeFromCart() {
        PCComponents selected = tableViewCart.getSelectionModel().getSelectedItem();
        ComponentsCartCollection.removeSelected(selected);
        tableViewCart.refresh();
    }

    @FXML
    void checkout() {

    }

    /** Opens a popup window to show filter options of the tableview of products */

    @FXML
    void filterTableView() {
        PopupForTableView.showFilterOptions(tableViewProducts);
    }

    @FXML
    void logout() throws IOException {
        ComponentsCollection.clearCollection();
        ComponentsCollection.setModified(false);
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

    /** Opens a popup window to show all components the user wants to compare */

    @FXML
    void compare(){
        ObservableList<PCComponents> selected = tableViewProducts.getSelectionModel().getSelectedItems();
        if(selected.isEmpty()) {
            AlertDialog.showWarningDialog("Choose components to compare","");
        } else if(selected.size() > 5){
            AlertDialog.showWarningDialog("You can only compare max 5 components","");
        } else if(selected.size() < 2){
            AlertDialog.showWarningDialog("You must choose at least 2 components", "");
        } else {
            PopupForComponents.compareComponent(selected);
        }
    }

    /** Let's the user create their own pc configurations */

    @FXML
    void addCustomConfiguration(){

    }

    /** searches through the tableview with the given search input */
    void search() {
        ComponentsCollection.collectionSearch(searchInput, tableViewProducts);
    }

    /** Detects a double click on a row in the tableview
     * and opens a new window to show the component details.*/

    public void showComponentOnDoubleClick(TableView<PCComponents> tableView){
        tableView.setRowFactory(tv -> {
            TableRow<PCComponents> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2) {
                    if(row.getItem() != null) {
                        // Component to show
                        PCComponents componentToShow = row.getItem();
                        // opens a popup window to show component details
                        PopupForComponents.showComponent(componentToShow);
                    }
                }
            });
            return row;
        });
    }
}
