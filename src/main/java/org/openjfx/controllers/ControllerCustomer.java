package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.fileUtilities.FileHandlers.FileActions;

import java.io.File;
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
        file.open(defaultData, "Loading system data...");
        // Initializes the tableview.
        ComponentsCollection.setTableView(tableViewProducts);
    }

    @FXML
    void addToCart() {

    }

    @FXML
    void checkout() {

    }

    @FXML
    void filterTableView() {

    }

    @FXML
    void logout() {

    }

    @FXML
    void openFile() {

    }

    @FXML
    void removeFromCart() {

    }

    @FXML
    void saveChanges() {

    }

    @FXML
    void saveFile() {

    }
}
