package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class PopupFilterTableViewController {

    @FXML private CheckBox checkProcessors;
    @FXML private CheckBox checkRam;
    @FXML private CheckBox checkKeyboards;
    @FXML private CheckBox checkMouse;
    @FXML private CheckBox checkGraphicCards;
    @FXML private CheckBox checkCabinet;
    @FXML private CheckBox checkOthers;
    @FXML private CheckBox checkLess500;
    @FXML private CheckBox checkLess1000;
    @FXML private CheckBox checkMore1000;

    @FXML
    void applyFilters() {

    }
}
