package org.openjfx.custom_exceptions;

public class UnsupportedFileReader extends IllegalArgumentException {
    public UnsupportedFileReader(String msg){
        super(msg);
    }
}
