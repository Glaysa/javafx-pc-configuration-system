package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataModels.PCComponents;

/** This controller is used for the popup window of components when being shown. */

public class PopupShowComponentController{

    @FXML private Button button;
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

    public void setButtonAsAddToCart(PCComponents componentToShow){
        button.setText("Add to cart");
        button.setOnAction((e) -> {
            ComponentsCartCollection.addToCollection(componentToShow);
        });
    }

    public void setButtonAsSelect(PCComponents selected){
        button.setText("Select");
        button.setOnAction((e) -> {
            System.out.println("Selected");
        });
    }

    public void hideButton(){
        button.setDisable(true);
        button.setOpacity(0);
    }
}
