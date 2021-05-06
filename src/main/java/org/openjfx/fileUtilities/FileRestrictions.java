package org.openjfx.fileUtilities;

/** This class is responsible for checking if the file contains data that is not allowed. */

public class FileRestrictions {

    protected static String restrictedDataKey;

    /** Used for bin files */
    protected static void checkIfRestricted(String objectIDKey){
        if(objectIDKey.equals(restrictedDataKey)) {
            throw new IllegalArgumentException("Restricted Data!\nYou don't have the right user privileges.");
        }
    }

    /** Get / Set methods */
    public static void setRestrictedDataKey(String objectIDKey){
        restrictedDataKey = objectIDKey;
    }
}