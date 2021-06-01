package org.openjfx.controllers.popupControllers;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataCollection.ConfigurationCartCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.fileUtilities.FileHandlers.FileActions;
import org.openjfx.guiUtilities.AlertDialog;
import java.io.File;
import java.util.ArrayList;

public class PopupCheckoutController {

    @FXML private VBox vBoxComponents;
    @FXML private VBox vBoxConfigurations;

    /* TODO: Needs improvements */

    @FXML
    void saveReceipt(){
        String totalPrice = "\nTotal Price: " + ComponentsCartCollection.getTotalPrice() + ConfigurationCartCollection.getTotalPrice() + "kr";
        ArrayList<Object> purchasedProducts = new ArrayList<>();
        purchasedProducts.addAll(ComponentsCartCollection.getCartObsList());
        purchasedProducts.addAll(ConfigurationCartCollection.getCartObsList());
        purchasedProducts.add(totalPrice);

        FileActions fileActions = new FileActions();
        FileChooser fileChooser = fileActions.getFileChooser();
        File fileToSave = fileChooser.showSaveDialog(new Stage());
        if(fileToSave == null) {
            AlertDialog.showWarningDialog("No file was chosen","");
        } else {
            fileActions.save(purchasedProducts, fileToSave, "Saving receipt...");
        }
    }

    /** Loads product details */

    public void loadComponentCartItems(ArrayList<PCComponents> components){
        if(!components.isEmpty()){
            HBox.setHgrow(vBoxComponents, Priority.ALWAYS);
            createComponentList(components, vBoxComponents);
            createTotalPrice(vBoxComponents, ComponentsCartCollection.getTotalPrice());
        } else {
            createTitle("Components Cart is empty!", vBoxComponents);
        }
    }

    public void loadConfigurationCartItems(ArrayList<PCConfigurations> configurations){
        if(!configurations.isEmpty()){
            HBox.setHgrow(vBoxConfigurations, Priority.ALWAYS);
            for(PCConfigurations config: configurations){
                VBox wrapper = new VBox();
                wrapper.setSpacing(5);
                createTitle(config.getConfigurationName(), wrapper);
                createComponentList(config.getPcComponents(), wrapper);
                createTotalPrice(wrapper, config.getTotalPrice());
                vBoxConfigurations.getChildren().add(wrapper);
            }
        } else {
            createTitle("Configurations Cart is empty!", vBoxConfigurations);
        }
    }

    /** Methods that create nodes needed to show product details */

    void createComponentList(ArrayList<PCComponents> components, VBox container){
        for(PCComponents component: components){
            HBox wrapper = new HBox();
            wrapper.setSpacing(20);
            Label componentName = createLabel(component.getComponentName());
            Label componentPrice = createLabel(component.getComponentPrice() + " kr");
            wrapper.getChildren().add(componentName);
            wrapper.getChildren().add(componentPrice);
            container.getChildren().add(wrapper);
        }
    }

    void createTitle(String title, VBox container){
        Label label = createHeaderLabel(title);
        container.getChildren().add(label);
        container.getChildren().add(new Separator(Orientation.HORIZONTAL));
    }

    void createTotalPrice(VBox container, double price){
        container.getChildren().add(new Separator(Orientation.HORIZONTAL));
        HBox wrapper = new HBox();
        wrapper.setSpacing(20);
        Label priceTitle = createHeaderLabel("TotalPrice: ");
        Label totalPrice = createHeaderLabel( price + " kr");
        wrapper.getChildren().add(priceTitle);
        wrapper.getChildren().add(totalPrice);
        container.getChildren().add(wrapper);
    }

    Label createLabel(String text){
        Label label = new Label(text);
        label.setPrefWidth(400);
        label.setStyle("-fx-font-size: 14px");
        return label;
    }

    Label createHeaderLabel(String text){
        Label label = new Label(text);
        label.setPrefWidth(400);
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
        return label;
    }
}
