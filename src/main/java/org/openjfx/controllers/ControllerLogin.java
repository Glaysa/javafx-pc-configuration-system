package org.openjfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.openjfx.App;
import org.openjfx.gui_utilities.Dialogs;

import java.io.IOException;

/** Just a simple class that directs the admin and customer to the correct fxml. */
/* TODO: Implement a working login system where users can register and login with correct credentials. */

public class ControllerLogin {

    @FXML private TextField password;
    @FXML private TextField username;
    @FXML private CheckBox administrator;

    @FXML
    void login(ActionEvent event) throws IOException {
        String passwordInput = password.getText();
        String usernameInput = username.getText();
        boolean isAdmin = administrator.isSelected();

        final String password = "1234";
        final String username1 = "admin";
        final String username2 = "user";

        boolean validAdmin = isAdmin && passwordInput.equals(password) && usernameInput.equals(username1);
        boolean validUser = passwordInput.equals(password) && usernameInput.equals(username2);

        if(validAdmin) {
            App.setRoot("admin");
        } else if(validUser) {
            App.setRoot("customer");
        } else {
            Dialogs.showWarningDialog("Wrong username/password combination");
        }
    }
}
