package org.openjfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.data_models.PCComponents;
import org.openjfx.gui_utilities.Dialogs;

public class ControllerAdmin {

    @FXML private TextField cName;
    @FXML private TextArea cDesc;
    @FXML private TextField price;
    @FXML private ComboBox<?> cType;

    @FXML
    void addComponent(ActionEvent event) {
        String componentName = cName.getText();
        String componentDesc = cDesc.getText();
        String componentPrice = price.getText();

        try {
            PCComponents c = new PCComponents(componentName,componentDesc,"type",componentPrice);
            System.out.println(c.toString());
            resetFields();

            Dialogs.showSuccessDialog("Component Added Successfully!");
        } catch (IllegalArgumentException e) {
            Dialogs.showWarningDialog(e.getMessage());
        }
    }

    void resetFields(){
        cName.setText("");
        cDesc.setText("");
        price.setText("");
        System.out.println("A new component has been added!");
    }
}
