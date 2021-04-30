package org.openjfx.controllers.popupControllers;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.guiUtilities.popupDialogs.PopupUtilities;
import java.util.ArrayList;

/** This controller is used for the popup window of components when being compared. */

public class PopupCompareComponentsController{

    @FXML HBox hBox;
    @FXML AnchorPane parentPane;
    private final ArrayList<Node> list = new ArrayList<>();;

    /** Takes all component details in adds it on a vBox */
    public void loadComponentsToCompare(PCComponents component){
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setPrefWidth(200);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-padding: 20px 20px 20px 20px");
        HBox.setHgrow(vBox, Priority.ALWAYS);

        HBox addToCartWrapper = createButton(component);
        Label id = createLabel(Integer.toString(component.getPCComponentID()));
        Label name = createLabel(component.getComponentName());
        Label type = createLabel(component.getComponentType());
        Label price = createLabel(Double.toString(component.getComponentPrice()));
        Label specs = createLabel(component.getComponentSpecs());

        vBox.getChildren().add(addToCartWrapper);
        vBox.getChildren().add(id);
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(name);
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(type);
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(price);
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(specs);
        list.add(vBox);
    }

    /** All components vBoxes are displayed in h box to be compared */
    public void compareComponents(){
        for(Node component: list){
            Separator s = new Separator();
            s.setOrientation(Orientation.VERTICAL);
            hBox.getChildren().add(component);
            hBox.getChildren().add(s);
        }
    }

    /** Label used to display the details on the vbox */
    private Label createLabel(String text){
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 14px;");
        return label;
    }

    /** Along with the component labels, a button is also shown to let the user add it to the cart */
    private HBox createButton(PCComponents component){
        HBox wrapper = new HBox();
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setStyle("-fx-font-size: 14px;");

        addToCartBtn.setOnAction((e) -> {
            ComponentsCartCollection.addToCollection(component);
            PopupUtilities.closePopup(parentPane);
        });

        wrapper.getChildren().add(addToCartBtn);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }
}
