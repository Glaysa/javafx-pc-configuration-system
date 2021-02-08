package org.openjfx.custom_exceptions;

public class UnsupportedFileParser extends IllegalArgumentException {
    public UnsupportedFileParser(String msg) {
        super(msg);
    }
}
