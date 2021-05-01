package org.openjfx.dataValidator;

import javafx.collections.ObservableList;
import org.openjfx.dataCollection.ConfigurationCollection;
import org.openjfx.dataModels.PCComponents;
import org.openjfx.dataModels.PCConfigurations;
import org.openjfx.guiUtilities.AlertDialog;
import java.util.ArrayList;
import java.util.Arrays;

/** This class is responsible for validating configured PC added on a tableview. */

public class ConfigurationValidator {

    public static void validateConfiguredPC(PCConfigurations toValidate){
        // Must have components when configuring a PC
        String[] mustHave = {"CPU","Cooler","Motherboard","RAM", "Storage","Case","Power Supply"};
        ArrayList<String> mustHaveComponents = new ArrayList<>(Arrays.asList(mustHave));

        // Removes type from mustHave[] if is selected
        for(PCComponents item: toValidate.getPcComponents()) {
            mustHaveComponents.removeIf(c -> c.equals(item.getComponentType()));
        }

        // When mustHave[] is empty, it means all component needed to configure a PC is added
        if(!mustHaveComponents.isEmpty()) {
            StringBuilder missing = new StringBuilder();
            for(String m: mustHaveComponents) {
                missing.append(m).append(", ");
            }
            throw new IllegalArgumentException("Missing components: " + missing);
        }
    }

    public static void validateConfigurationComponent(PCComponents toValidate){
        ObservableList<PCComponents> itemsObsList = ConfigurationCollection.getItemsObsList();

        // The user can only add one component of each type when configuring a PC
        if(itemsObsList.contains(toValidate)) {
            throw new IllegalArgumentException(toValidate.getComponentName() + " already selected!");
        } else {
            boolean itemRemoved = itemsObsList.removeIf(p -> p.getComponentType().equals(toValidate.getComponentType()));
            if(itemRemoved) AlertDialog.showSuccessDialog(toValidate.getComponentType() + " has been replaced");
        }
    }

}
