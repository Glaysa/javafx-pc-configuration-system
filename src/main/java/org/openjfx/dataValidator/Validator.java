package org.openjfx.dataValidator;

import org.openjfx.customExceptions.InvalidNumberException;

/** This class is responsible for validating components added on the tableview. */

public class Validator {

    private static String componentName;
    private static String componentType;
    private static String componentSpecs;
    private static double componentPrice;

    private static String removeDelimiter(String toValidate){
        if(toValidate.contains(",")){
            toValidate = toValidate.replace(',','-');
        }
        return toValidate;
    }

    public static void validateComponentName(String componentName) {

        Validator.componentName = removeDelimiter(componentName);
        if(componentName.isEmpty()){
            throw new IllegalArgumentException("Component name cannot be empty");
        } else if(componentName.length() < 3) {
            throw new IllegalArgumentException("Component name must be at least 3 characters");
        }
        Validator.componentName = Character.toString(componentName.charAt(0)).toUpperCase()+componentName.substring(1);
    }

    public static void validateComponentType(String componentType) {

        Validator.componentType = removeDelimiter(componentType);
        if(componentType.isEmpty()){
            throw new IllegalArgumentException("Component type cannot be empty");
        } else if(componentType.length() < 3) {
            throw new IllegalArgumentException("Component type must be at least 3 characters");
        }
        Validator.componentType = Character.toString(componentType.charAt(0)).toUpperCase()+componentType.substring(1);
    }

    public static void validateComponentSpecs(String componentSpecs) {

        Validator.componentSpecs = removeDelimiter(componentSpecs);
        if(componentSpecs.isEmpty()){
            throw new IllegalArgumentException("Component specifications cannot be empty");
        } else if(componentSpecs.length() < 3) {
            throw new IllegalArgumentException("Component specifications must be at least 3 characters");
        }
        Validator.componentSpecs = Character.toString(componentSpecs.charAt(0)).toUpperCase()+componentSpecs.substring(1);
    }

    public static void validateComponentPrice(String txtComponentPrice){
        try {
            if(txtComponentPrice.contains(",")){
                txtComponentPrice = txtComponentPrice.replace(',','.');
            }
            double componentPrice = Double.parseDouble(txtComponentPrice);
            if(componentPrice <= 0) { throw new InvalidNumberException("Price must greater than 0"); }
            Validator.componentPrice = componentPrice;

        } catch (InvalidNumberException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid price format");
        }
    }

    public static String getComponentName(){ return componentName; }
    public static String getComponentType(){ return componentType; }
    public static String getComponentSpecs(){ return componentSpecs; }
    public static double getComponentPrice(){ return componentPrice; }
}
