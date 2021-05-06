package org.openjfx.fileUtilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import java.lang.reflect.Type;

/** This class is responsible for parsing text files */

public class FileParser {

    /** Converts string to object */
    public static Object parseObject(String dataToParse){
        Gson gson = new Gson();
        if(checkObjectKey(dataToParse, PCComponents.getObjectIDKey())) {
            return gson.fromJson(dataToParse, (Type) PCComponents.class);
        } else if(checkObjectKey(dataToParse, PCConfigurations.getObjectIDKey())){
            return gson.fromJson(dataToParse, (Type) PCConfigurations.class);
        } else {
            throw new IllegalArgumentException("File is corrupted!");
        }
    }

    /** Checks which object type the string needs to be converted to by checking the object id key */
    static boolean checkObjectKey(String jsonString, String objectIDKey){
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            return jsonObject.keySet().contains(objectIDKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("The system cannot parse the file contents.");
        }
    }
}
