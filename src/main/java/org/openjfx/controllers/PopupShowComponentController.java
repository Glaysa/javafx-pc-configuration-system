package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.openjfx.dataModels.PCComponents;

/** This controller is used for the popup window of components when being shown. */

public class PopupShowComponentController{

    @FXML private Button addToCart;
    @FXML private Label labelName;
    @FXML private Label labelId;
    @FXML private Label labelType;
    @FXML private Label labelPrice;
    @FXML private Label labelDescription;

    /** Display the data */
    public void setComponentToShow(PCComponents componentToShow){
        labelId.setText(Integer.toString(componentToShow.getPCComponentID()));
        labelName.setText(componentToShow.getComponentName());
        labelType.setText(componentToShow.getComponentType());
        labelPrice.setText(Double.toString(componentToShow.getComponentPrice()));
        labelDescription.setText(componentToShow.getComponentSpecs());
    }

    /** Get the add to cart button */
    public Button getAddToCartBtn(){
        return addToCart;
    }

}

