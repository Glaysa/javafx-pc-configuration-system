package org.openjfx.fileUtilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;

/** This class is responsible for parsing data. */

public class FileParser {

    public static void objectParser(String dataToParse, String restrictedData){
        if(containsRestrictedData(dataToParse, restrictedData)) {
            throw new IllegalArgumentException("Restricted Content");
        } else {
            if(containsValidData(dataToParse, PCComponents.getObjectIDKey())) {
                System.out.println("Components Data");
            } else if(containsValidData(dataToParse, PCConfigurations.getObjectIDKey())) {
                System.out.println("Configurations Data");
            } else {
                throw new IllegalArgumentException("Unreadable Contents!");
            }
        }
    }

    public static boolean containsValidData(String jsonString, String keyToCheck){
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        return jsonObject.keySet().contains(keyToCheck);
    }

    public static boolean containsRestrictedData(String jsonString, String restrictedDataKey){
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        return jsonObject.keySet().contains(restrictedDataKey);
    }
}