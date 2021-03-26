package org.openjfx.custom_exceptions;

public class InvalidNumberException extends IllegalArgumentException {
    public InvalidNumberException(String msg){
        super(msg);
    }
}
