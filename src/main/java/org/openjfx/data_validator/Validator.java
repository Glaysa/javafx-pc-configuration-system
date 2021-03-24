package org.openjfx.data_validator;

import java.util.regex.Pattern;

/** This class is responsible for validating components added on the tableview. */

public class Validator {

    private static int componentNumber;
    private static String componentName;
    private static String componentType;
    private static String componentSpecs;
    private static double componentPrice;

    private static String replaceComma(String toValidate){
        if(toValidate.contains(",")){
            toValidate = toValidate.replace(',','-');
        }
        return toValidate;
    }

    public static void validate_componentNumber(String txtComponentNumber){
        try {
            componentNumber = Integer.parseInt(txtComponentNumber);
            if(componentNumber < 0) {
                throw new NumberFormatException("Component number must be greater than 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid component number"); }
    }

    public static void validate_componentName(String componentName) {

        if(componentName.isEmpty()){
            throw new IllegalArgumentException("Component name cannot be empty");
        }
        String newComponentName = Character.toString(componentName.charAt(0)).toUpperCase()+componentName.substring(1);
        Validator.componentName = replaceComma(newComponentName);
    }

    public static void validate_componentType(String componentType) {
        boolean isCorrect = Pattern.matches("^[A-ZÆØÅ]?.+$",componentType) && !componentType.contains(",");
        if(!isCorrect){
            throw new IllegalArgumentException("Component type must be in the correct format and not empty"); }
        Validator.componentType = componentType;
    }

    public static void validate_componentSpecs(String componentSpecs) {
        if(componentSpecs.isEmpty()){ throw new IllegalArgumentException("Specifications cannot be empty"); }
        String newComponentSpecs = Character.toString(componentSpecs.charAt(0)).toUpperCase()
                +componentSpecs.substring(1);
        Validator.componentSpecs = replaceComma(newComponentSpecs);
    }

    public static void validate_componentPrice(String txtComponentPrice){
        try {
            if(txtComponentPrice.contains(",")){
                String strPrice = txtComponentPrice.replace(',','.');
                double componentPrice = Double.parseDouble(strPrice);
                if(componentPrice <= 0) { throw new NumberFormatException("Price must greater than 0"); }
                Validator.componentPrice = componentPrice;
            } else {
                double componentPrice = Double.parseDouble(txtComponentPrice);
                if(componentPrice <= 0) { throw new NumberFormatException("Price must greater than 0"); }
                Validator.componentPrice = componentPrice;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid price format");
        }
    }

    public static int getComponentNumber(){ return componentNumber; }
    public static String getComponentName(){ return componentName; }
    public static String getComponentType(){ return componentType; }
    public static String getComponentSpecs(){ return componentSpecs; }
    public static double getComponentPrice(){ return componentPrice; }
}
