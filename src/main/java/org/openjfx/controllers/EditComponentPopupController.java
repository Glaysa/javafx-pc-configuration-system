package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.data_collection.ComponentsCollection;
import org.openjfx.data_models.PCComponents;
import org.openjfx.gui_utilities.AlertDialog;
import java.net.URL;
import java.util.ResourceBundle;

/** This controller is used for the popup window of components when being edited. */

public class EditComponentPopupController implements Initializable {

    @FXML private AnchorPane parentPane;
    @FXML private Button updatedComponentBtn;
    @FXML private TextField componentNumber;
    @FXML private TextField componentName;
    @FXML private TextArea componentSpecs;
    @FXML private ComboBox<String> typeOptions;
    @FXML private TextField componentPrice;
    private PCComponents updatedComponent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fills the component type combobox with values.
        ComponentsCollection.fillCombobox_TYPE(typeOptions);
        // (listener) check methods to see its functions
        ComponentsCollection.collectionOnChange(typeOptions);
        // Component Number cannot be edited
        componentNumber.setDisable(true);
    }

    /** Updates the component */

    @FXML
    public void updateComponent(){
        try {
            int numberStr = Integer.parseInt(componentNumber.getText());
            String nameStr = componentName.getText();
            String typeStr = typeOptions.getValue();
            String specsStr = componentSpecs.getText();
            String priceStr = componentPrice.getText();
            updatedComponent = new PCComponents(numberStr, nameStr, typeStr, specsStr, priceStr);
        } catch (Exception e) {
            AlertDialog.showWarningDialog(e.getMessage(),"");
        }
    }

    /** Closes the popup window */
    @FXML
    private void cancelEditComponent(){
        Stage stage = (Stage) parentPane.getScene().getWindow();
        stage.close();
    }

    /** Displays the data of component to be edited */
    public void setComponentToEdit(PCComponents componentToEdit) {
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
}
