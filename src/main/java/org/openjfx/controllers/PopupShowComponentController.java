package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.openjfx.dataModels.PCComponents;

public class PopupShowComponentController{

    @FXML private Label labelName;
    @FXML private Label labelId;
    @FXML private Label labelType;
    @FXML private Label labelPrice;
    @FXML private Label labelDescription;

    public void setComponentToShow(PCComponents componentToShow){
        labelId.setText(Integer.toString(componentToShow.getPCComponentID()));
        labelName.setText(componentToShow.getComponentName());
        labelType.setText(componentToShow.getComponentType());
        labelPrice.setText(Double.toString(componentToShow.getComponentPrice()));
        labelDescription.setText(componentToShow.getComponentSpecs());
    }
}

