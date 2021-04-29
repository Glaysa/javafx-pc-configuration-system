package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataModels.PCComponents;
import java.util.ArrayList;

public class PopupCompareComponentsController{

    @FXML HBox hBox;
    @FXML AnchorPane parentPane;
    private final ArrayList<Node> list = new ArrayList<>();;

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

    public void compareComponents(){
        for(Node component: list){
            Separator s = new Separator();
            s.setOrientation(Orientation.VERTICAL);
            hBox.getChildren().add(component);
            hBox.getChildren().add(s);
        }
    }

    private Label createLabel(String text){
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 14px;");
        return label;
    }

    private HBox createButton(PCComponents component){
        HBox wrapper = new HBox();
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setStyle("-fx-font-size: 14px;");

        addToCartBtn.setOnAction((e) -> {
            ComponentsCartCollection.addToCollection(component);
            closePopup();
        });

        wrapper.getChildren().add(addToCartBtn);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }

    private void closePopup(){
        Stage stage = (Stage) parentPane.getScene().getWindow();
        stage.close();
    }
}
