package org.openjfx.guiUtilities;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.concurrent.Callable;

public class OnEnterKeyEvent {

    /** When the ENTER key is pressed on a given jfx control (text field, etc.)
     *  a certain method is executed. */

    public static <T extends Control> void execute(T object, Callable<Void> method){
        object.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER) {
                    try {
                        method.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
