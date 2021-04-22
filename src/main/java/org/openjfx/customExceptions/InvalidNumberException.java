package org.openjfx.customExceptions;

public class InvalidNumberException extends IllegalArgumentException {
    public InvalidNumberException(String msg){
        super(msg);
    }
}
