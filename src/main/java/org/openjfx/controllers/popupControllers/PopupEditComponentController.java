package org.openjfx.controllers.popupControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.OnEnterKeyEvent;
import org.openjfx.guiUtilities.popupDialogs.PopupUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/** This controller is used for the popup window of components when being edited. */

public class PopupEditComponentController implements Initializable, Callable<Void> {

    @FXML private AnchorPane parentPane;
    @FXML private Button updateComponentBtn;
    @FXML private TextField componentNumber;
    @FXML private TextField componentName;
    @FXML private TextArea componentSpecs;
    @FXML private ComboBox<String> typeOptions;
    @FXML private TextField componentPrice;
    private PCComponents updatedComponent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fills the component type combobox with values.
        ComponentsCollection.fillComponentTypeObsList();
        ComponentsCollection.fillComponentTypeComboBox(typeOptions);
        // (listener) check methods to see its functions
        ComponentsCollection.collectionOnChange(typeOptions);
        // Component Number cannot be edited
        componentNumber.setDisable(true);
        // Initializes key event listener
        onEnterKey();
    }

    /** Initializes callable - this updateComponent() runs when ENTER key is pressed */

    @Override
    public Void call() {
        updateComponentBtn.fire();
        return null;
    }

    /** Sets on which jfx controls do we want to listen for an enter key event */
    public void onEnterKey(){
        Callable<Void> callable = this; // call()
        OnEnterKeyEvent.execute(componentNumber, callable);
        OnEnterKeyEvent.execute(componentName, callable);
        OnEnterKeyEvent.execute(typeOptions, callable);
        OnEnterKeyEvent.execute(componentPrice, callable);
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
        PopupUtilities.closePopup(parentPane);
    }

    /** Displays the data of component to be edited */
    public void setComponentToEdit(PCComponents componentToEdit) {
        this.componentNumber.setText(Integer.toString(componentToEdit.getPCComponentID()));
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
    public Button getUpdateComponentBtn(){
        return updateComponentBtn;
    }
}
