package org.openjfx.guiUtilities.popupDialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.App;
import org.openjfx.controllers.popupControllers.PopupCheckoutController;
import org.openjfx.dataCollection.ComponentsCartCollection;
import org.openjfx.dataCollection.ConfigurationCartCollection;

public class PopupForCheckout {

    /** Opens a popup window to show cart summary */
    public static void showCheckout(){
        try {
            String fileUrl = PopupUtilities.getPopupPath("popupCheckout");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileUrl));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            PopupCheckoutController controller = fxmlLoader.getController();
            controller.loadComponentCartItems(ComponentsCartCollection.getCartArraylist());
            controller.loadConfigurationCartItems(ConfigurationCartCollection.getCartArraylist());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Checkout");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
