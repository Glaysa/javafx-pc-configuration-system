package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.gui_utilities.Dialogs;
import java.net.URL;
import java.util.ResourceBundle;

/** This controller is used for the popup window of components when are being edited. */

public class PopupControllerEditComponents implements Initializable {

    @FXML private Button updatedComponentBtn;
    @FXML private TextField componentNumber;
    @FXML private TextField componentName;
    @FXML private TextArea componentSpecs;
    @FXML private ComboBox<String> typeOptions;
    @FXML private TextField componentPrice;
    private PCComponents componentToEdit;
    private PCComponents updatedComponent;
    private boolean updated = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fills the component type combobox with values.
        ComponentsCollection.fillCombobox_TYPE(typeOptions);
        // Whenever there is a new component type, they are added to the component type combobox
        ComponentsCollection.fillCombobox_TYPE_listOnChanged(typeOptions);
    }

    /** Updates the component */

    @FXML
    public void updateComponent(){
        try {
            String numberStr = componentNumber.getText();
            String nameStr = componentName.getText();
            String typeStr = typeOptions.getValue();
            String specsStr = componentSpecs.getText();
            String priceStr = componentPrice.getText();
            updatedComponent = new PCComponents(numberStr, nameStr, typeStr, specsStr, priceStr);

        } catch (Exception e) {
            Dialogs.showWarningDialog(e.getMessage(),"");
        }
        updated = true;
    }

    /** Displays the data of component to be edited */

    public void setComponentToEdit(PCComponents componentToEdit) {
        this.componentToEdit = componentToEdit;
        this.componentNumber.setText(Integer.toString(componentToEdit.getComponentNumber()));
        this.componentName.setText(componentToEdit.getComponentName());
        this.typeOptions.setValue(componentToEdit.getComponentType());
        this.componentSpecs.setText(componentToEdit.getComponentSpecs());
        this.componentPrice.setText(Double.toString(componentToEdit.getComponentPrice()));
    }

    /** Gets the new updated component */

    public PCComponents getUpdatedComponent(){
        return updatedComponent;
    }

    /** Gets the update button */

    public Button getUpdatedComponentBtn(){
        return updatedComponentBtn;
    }

    public boolean isUpdated() {
        return updated;
    }
}
