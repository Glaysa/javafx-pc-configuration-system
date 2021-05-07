package org.openjfx.fileUtilities;

import java.util.UUID;

/** This class is responsible for checking if the file contains data that is not allowed. */

public class FileRestrictions {

    protected static UUID restrictedDataKey;

    /** Used for bin files */
    protected static void checkIfRestricted(UUID objectUUID){
        if(objectUUID.equals(restrictedDataKey)) {
            throw new IllegalArgumentException("Restricted Data!\nYou don't have the right user privileges.");
        }
    }

    /** Get / Set methods */
    public static void setRestrictedDataKey(UUID objectUUID){
        restrictedDataKey = objectUUID;
    }
}