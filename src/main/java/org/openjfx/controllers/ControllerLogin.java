package org.openjfx.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.openjfx.App;
import org.openjfx.gui_utilities.Dialogs;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Just a simple class that directs the admin and customer to the correct fxml. */
/* TODO: Implement a working login system where users can register and login with correct credentials. */

public class ControllerLogin implements Initializable {

    @FXML private TextField password;
    @FXML private TextField username;

    /** Initializes the the key event on loginOnEnterKey(). */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginOnEnterKey();
    }

    /** Method that directs user to either the admin view or customer view. */

    @FXML
    void login() throws IOException {
        String passwordInput = password.getText();
        String usernameInput = username.getText();

        if(usernameInput.equals("admin") && passwordInput.equals("1234")) {
            App.setRoot("admin");
        } else if (!usernameInput.equals("admin") && passwordInput.equals("1234")) {
            App.setRoot("customer");
        } else {
            Dialogs.showWarningDialog("Wrong password/username combination","");
        }
    }

    /** Method that runs login() when ENTER key is pressed. */

    void loginOnEnterKey(){
        password.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER) {
                    try {
                        login();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
