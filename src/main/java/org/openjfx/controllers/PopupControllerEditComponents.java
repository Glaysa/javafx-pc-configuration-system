package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import java.net.URL;
import java.util.ResourceBundle;

public class PopupControllerEditComponents implements Initializable {

    @FXML private TextField componentNumber;
    @FXML private TextField componentName;
    @FXML private TextArea componentSpecs;
    @FXML private ComboBox<String> typeOptions;
    @FXML private TextField componentPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fills the component type combobox with values.
        ComponentsCollection.fillCombobox_TYPE(typeOptions);
        // Whenever there is a new component type, they are added to the component type combobox
        ComponentsCollection.fillCombobox_TYPE_listOnChanged(typeOptions, null);
    }

    public void setComponentToEdit(PCComponents componentToEdit) {
        this.componentNumber.setText(Integer.toString(componentToEdit.getComponentNumber()));
        this.componentName.setText(componentToEdit.getComponentName());
        this.typeOptions.setValue(componentToEdit.getComponentType());
        this.componentSpecs.setText(componentToEdit.getComponentSpecs());
        this.componentPrice.setText(Double.toString(componentToEdit.getComponentPrice()));
    }
}
