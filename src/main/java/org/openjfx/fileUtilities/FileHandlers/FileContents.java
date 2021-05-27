package org.openjfx.fileUtilities.FileHandlers;

import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.fileUtilities.FileParser;
import org.openjfx.fileUtilities.FileRestrictions;
import org.openjfx.guiUtilities.AlertDialog;
import org.openjfx.guiUtilities.Indicators;
import java.util.ArrayList;

/** This class is responsible for handling the contents of a file when opened.
 *  The system can only process files containing PC Component objects and PC Configuration Objects.
 *  If the file contains text, the file will be parsed and if valid, the contents will be converted to objects.
 *  if NOT valid, an exception will be thrown. */

public class FileContents extends FileRestrictions {

    /** Used to get access of the current opened file */
    private static final FileThreads fileThreads = FileThreads.getInstance();

    /** Checks if the file contains a valid data */
    public static void processData(ArrayList<Object> data){
        try {
            if(data.isEmpty()) throw new IllegalArgumentException("File is empty!");
            processContents(data);
        } catch (Exception e) {
            AlertDialog.showWarningDialog(e.getMessage(),"Please try again!");
        }
    }

    /** Processes the file contents, display to tableview, etc. */
    static void processContents(ArrayList<Object> data){
        Object object = data.get(0);
        if(object instanceof PCComponents) {
            FileRestrictions.checkIfRestricted(PCComponents.getUUID());
            fileThreads.setLastOpenedFile(fileThreads.getCurrentOpenedFile());
            loadComponents(data);
        } else if(object instanceof PCConfigurations) {
            FileRestrictions.checkIfRestricted(PCConfigurations.getUUID());
            fileThreads.setLastOpenedFile(fileThreads.getCurrentOpenedFile());
            loadConfiguration(data);
        } else {
            parseContents(data);
        }
    }

    /** If file contains text, convert text to object and try processing the parsed data */
    static void parseContents(ArrayList<Object> data){
        ArrayList<Object> parsedData = new ArrayList<>();
        for(Object object: data){
            parsedData.add(FileParser.parseObject(object.toString()));
        }
        processContents(parsedData);
    }

    /** If the file contains PC Components, display it on the tableView */
    static void loadComponents(ArrayList<Object> data){
        ComponentsCollection.clearCollection();
        for(Object object: data){
            ComponentsCollection.addToCollection((PCComponents) object);
        }
        Indicators.updateFileNameAtAdmin(fileThreads.getLastOpenedFile().getName());
        Indicators.updateFileStatusAtAdmin(false);
    }

    /** If the file contains PC Configurations, display it on the tableView */
    static void loadConfiguration(ArrayList<Object> data){
        ConfigurationCollection.clearCollection();
        for(Object object: data){
            ConfigurationCollection.addConfiguration((PCConfigurations) object);
        }
        Indicators.updateFileNameAtUser(fileThreads.getLastOpenedFile().getName());
        Indicators.updateFileStatusAtUser(false);
    }
}
