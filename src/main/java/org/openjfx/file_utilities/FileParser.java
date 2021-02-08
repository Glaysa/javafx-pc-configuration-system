package org.openjfx.file_utilities;

import org.openjfx.data_models.PCComponents;

public class FileParser {

    public static <T> T setParser(String dataToParse){
        String[] attributes = dataToParse.split(";");
        switch (attributes.length) {
            case 4: return componentsParser(attributes);
            case 6: return configurationParser(attributes);
            default: return standardParser(dataToParse);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T standardParser(String data){
        return (T) data;
    }

    @SuppressWarnings("unchecked")
    public static <T> T componentsParser(String[] attributes){
        String a1 = attributes[0];
        String a2 = attributes[1];
        String a3 = attributes[2];
        String a4 = attributes[3];

        PCComponents c = new PCComponents(a1, a2, a3, a4);
        return (T) c;
    }

    @SuppressWarnings("unchecked")
    public static <T> T configurationParser(String[] dataToParse){
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
