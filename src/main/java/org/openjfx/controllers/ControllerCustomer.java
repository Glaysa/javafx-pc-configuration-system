package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.openjfx.dataModels.PCComponents;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {

    @FXML private Label totalPriceLabel;
    @FXML private TextField searchInput;
    @FXML private TableView<PCComponents> tableViewCart;
    @FXML private TableView<PCComponents> tableViewProducts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
