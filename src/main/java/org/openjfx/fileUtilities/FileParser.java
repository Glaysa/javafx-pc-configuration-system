package org.openjfx.fileUtilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;

/** This class is responsible for parsing text files */

public class FileParser {

    /** Converts string to object */
    public static Object parseObject(String dataToParse){
        Gson gson = new Gson();

        if(checkAllKeys(dataToParse, PCComponents.class)) {
            return gson.fromJson(dataToParse, (Type) PCComponents.class);
        } else if(checkAllKeys(dataToParse, PCConfigurations.class)){
            return gson.fromJson(dataToParse, (Type) PCConfigurations.class);
        } else {
            throw new IllegalArgumentException("File is corrupted!");
        }
    }

    /** Checks if all object attributes are given and valid */
    static boolean checkAllKeys(String jsonString, Type objectType){
        try {
            // Gets the attributes of a class based on the type given
            Field[] attributes = getDeclaredAttributes(objectType);
            // Converts file content to JsonObject
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            /* Checks if all JsonObject keys matches the given class's fields,
             if not, the file will be considered corrupted */
            if(attributes != null) {
                for(String key: jsonObject.keySet()){
                    boolean s = Arrays.stream(attributes).anyMatch(f -> f.getName().equals(key));
                    if(!s) return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException("The system cannot parse the file contents.");
        }
    }

    /** Returns the fields of a class based on the type given */
    static Field[] getDeclaredAttributes(Type objectType){
        if(objectType.equals(PCComponents.class)){
            return PCComponents.class.getDeclaredFields();
        } else if(objectType.equals(PCConfigurations.class)){
            return PCConfigurations.class.getDeclaredFields();
        } else {
            return null;
        }
    }
}
