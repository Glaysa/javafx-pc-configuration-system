package org.openjfx.controllers.popupControllers;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.openjfx.dataCollection.ConfigurationCartCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.popupDialogs.PopupUtilities;

public class PopupShowConfigurationController {

    @FXML AnchorPane parentPane;
    @FXML VBox vBoxConfigDetails;
    @FXML Button button;

    /** Creates nodes for configuration details */
    public void loadConfigurationDetails(PCConfigurations item){
        Label name = new Label("Name: " + item.getConfigurationName());
        name.setStyle("-fx-font-size: 20px");
        Label componentsList = new Label("Components: ");
        componentsList.setStyle("-fx-font-size: 20px");

        vBoxConfigDetails.setStyle("-fx-padding: 20px 20px 20px 20px");
        vBoxConfigDetails.getChildren().add(name);
        vBoxConfigDetails.getChildren().add(new Separator(Orientation.HORIZONTAL));
        vBoxConfigDetails.getChildren().add(componentsList);
        vBoxConfigDetails.getChildren().add(new Separator(Orientation.HORIZONTAL));

        for(PCComponents components: item.getPcComponents()){
            HBox wrapper = new HBox();
            wrapper.setSpacing(50);

            Label componentName = new Label(components.getComponentName());
            componentName.setPrefWidth(300);
            componentName.setStyle("-fx-font-size: 14px");
            Label componentPrice = new Label(components.getComponentPrice() + " kr");
            componentPrice.setAlignment(Pos.CENTER_RIGHT);
            componentPrice.setStyle("-fx-font-size: 14px");

            wrapper.getChildren().add(componentName);
            wrapper.getChildren().add(componentPrice);
            vBoxConfigDetails.getChildren().add(wrapper);
        }

        vBoxConfigDetails.getChildren().add(new Separator(Orientation.HORIZONTAL));
        Label price = new Label("Price: " + item.getTotalPrice() + " kr");
        price.setStyle("-fx-font-size: 20px");
        vBoxConfigDetails.getChildren().add(price);
    }

    /** When on the components tableview, the add to cart button is shown */
    public void setButtonAsAddToCart(PCConfigurations componentToShow){
        button.setText("Add to cart");
        button.setOnAction((e) -> {
            ConfigurationCartCollection.addToCollection(componentToShow);
            PopupUtilities.closePopup(parentPane);
        });
    }

    /** When on the components cart tableview, the remove from cart button is shown */
    public void setButtonAsRemoveFromCart(PCConfigurations toRemove){
        button.setText("Remove from Cart");
        button.setOnAction((e) -> {
            ConfigurationCartCollection.removeSelected(toRemove);
            PopupUtilities.closePopup(parentPane);
        });
    }
}
