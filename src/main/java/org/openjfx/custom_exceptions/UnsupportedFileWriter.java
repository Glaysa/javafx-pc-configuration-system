package org.openjfx.custom_exceptions;

public class UnsupportedFileWriter extends IllegalArgumentException {
    public UnsupportedFileWriter(String msg){
        super(msg);
    }
}
