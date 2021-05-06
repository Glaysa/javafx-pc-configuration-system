package org.openjfx.fileUtilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** This class is responsible for checking if the file contains data that is not allowed. */

public class FileRestrictions {

    protected static String restrictedDataKey;

    /** Used for bin files */
    protected static void checkRestrictions(String objectIDKey){
        if(objectIDKey.equals(restrictedDataKey)) {
            throw new IllegalArgumentException("Restricted Data!\nYou don't have the right user privileges.");
        }
    }

    /** Text files need to be deserialized first therefore has a different method to check restricted contents */
    protected static void parseAndCheckRestrictions(String jsonString){
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            if(jsonObject.keySet().contains(restrictedDataKey)) {
                throw new IllegalArgumentException("Restricted Data!\nYou don't have the right user privileges.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("The system cannot parse the file contents!");
        }
    }

    /** Get / Set methods */
    public static void setRestrictedDataKey(String objectIDKey){
        restrictedDataKey = objectIDKey;
    }
}