package org.openjfx.controllers.popupControllers;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import java.util.ArrayList;

public class PopupCheckoutController {

    @FXML private VBox vBoxComponents;
    @FXML private VBox vBoxConfigurations;

    @FXML
    void saveReceipt(){
        System.out.println("Saving receipt...");
    }

    public void loadComponentCartItems(ArrayList<PCComponents> components){
        HBox.setHgrow(vBoxComponents, Priority.ALWAYS);

        Label title = createHeaderLabel("Components Cart: ");
        vBoxComponents.getChildren().add(title);
        vBoxComponents.getChildren().add(new Separator(Orientation.HORIZONTAL));

        for(PCComponents component: components){
            HBox wrapper = new HBox();
            wrapper.setSpacing(20);
            Label componentName = createLabel(component.getComponentName());
            Label componentPrice = createLabel(component.getComponentPrice() + " kr");
            wrapper.getChildren().add(componentName);
            wrapper.getChildren().add(componentPrice);
            vBoxComponents.getChildren().add(wrapper);
        }

        vBoxComponents.getChildren().add(new Separator(Orientation.HORIZONTAL));
        HBox wrapper = new HBox();
        wrapper.setSpacing(20);
        Label priceTitle = createHeaderLabel("TotalPrice: ");
        Label totalPrice = createHeaderLabel(ComponentsCartCollection.getTotalPrice() + " kr");
        wrapper.getChildren().add(priceTitle);
        wrapper.getChildren().add(totalPrice);
        vBoxComponents.getChildren().add(wrapper);
    }

    public void loadConfigurationCartItems(ArrayList<PCConfigurations> configurations){
        HBox.setHgrow(vBoxConfigurations, Priority.ALWAYS);

        Label cartTitle = createHeaderLabel("Configurations Cart: ");
        vBoxConfigurations.getChildren().add(cartTitle);
        vBoxConfigurations.getChildren().add(new Separator(Orientation.HORIZONTAL));

        for(PCConfigurations config: configurations){

            Label configName = createLabel("Configured PC Name: " + config.getConfigurationName());
            vBoxConfigurations.getChildren().add(configName);
            vBoxConfigurations.getChildren().add(new Separator(Orientation.HORIZONTAL));

            Label configComponents = createLabel("Components: ");
            vBoxConfigurations.getChildren().add(configComponents);
            vBoxConfigurations.getChildren().add(new Separator(Orientation.HORIZONTAL));

            for(PCComponents component: config.getPcComponents()){
                HBox wrapper = new HBox();
                wrapper.setSpacing(20);
                Label componentName = createLabel(component.getComponentName());
                Label componentPrice = createLabel(component.getComponentPrice() + " kr");
                wrapper.getChildren().add(componentName);
                wrapper.getChildren().add(componentPrice);
                vBoxConfigurations.getChildren().add(wrapper);
            }

            vBoxConfigurations.getChildren().add(new Separator(Orientation.HORIZONTAL));
            HBox wrapper = new HBox();
            wrapper.setSpacing(20);
            Label priceTitle = createHeaderLabel("Total Price: ");
            Label totalPrice = createHeaderLabel(config.getTotalPrice() + " kr");
            wrapper.getChildren().add(priceTitle);
            wrapper.getChildren().add(totalPrice);
            vBoxConfigurations.getChildren().add(wrapper);
        }
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
        label.setStyle("-fx-font-size: 24px");
        return label;
    }
}
