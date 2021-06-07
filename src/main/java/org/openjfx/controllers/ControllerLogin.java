package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.openjfx.App;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.OnEnterKeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/** Just a simple class that directs the admin and customer to the correct fxml. */
/* TODO: Implement a working login system where users can register and login with correct credentials. */

public class ControllerLogin implements Initializable, Callable<Void> {

    @FXML private TextField password;
    @FXML private TextField username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AlertDialog.applyAlertStyles();
        // Initializes key event listener
        onEnterKey();
    }

    /** Method that runs login() when ENTER key is pressed. */
    @Override
    public Void call() throws IOException {
        login();
        return null;
    }

    /** Detects on enter key */
    public void onEnterKey(){
        Callable<Void> callable = this;
        OnEnterKeyEvent.execute(password, callable);
        OnEnterKeyEvent.execute(username, callable);
    }

    /** Method that directs user to either the admin view or customer view. */
    @FXML
    void login() throws IOException {
        String passwordInput = password.getText();
        String usernameInput = username.getText();

        if(usernameInput.equals("admin") && passwordInput.equals("1234")) {
            App.setRoot("admin", "Admin");
        } else if (usernameInput.equals("user") && passwordInput.equals("1234")) {
            App.setRoot("customer", "PC Configuration System");
        } else {
            AlertDialog.showWarningDialog("Wrong password/username combination","");
        }
    }
}
