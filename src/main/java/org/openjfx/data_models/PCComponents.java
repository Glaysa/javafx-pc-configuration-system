package org.openjfx.data_models;

import org.openjfx.data_validator.Validator;

/** Model of Pc components. */

public class PCComponents {

    private int componentNumber;
    private String componentName;
    private String componentType;
    private String componentSpecs;
    private double componentPrice;

    public PCComponents(String componentNumber, String componentName, String componentType, String componentSpecs, String componentPrice){
        Validator.validate_componentNumber(componentNumber);
        Validator.validate_componentName(componentName);
        Validator.validate_componentSpecs(componentSpecs);
        Validator.validate_componentType(componentType);
        Validator.validate_componentPrice(componentPrice);

        this.componentNumber = Validator.getComponentNumber();
        this.componentName = Validator.getComponentName();
        this.componentType = Validator.getComponentType();
        this.componentSpecs = Validator.getComponentSpecs();
        this.componentPrice = Validator.getComponentPrice();
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s\n", componentNumber, componentName, componentType, componentSpecs, componentPrice);
    }

    public void setComponentNumber(String componentNumber) {
        Validator.validate_componentNumber(componentNumber);
        this.componentNumber = Validator.getComponentNumber();
    }

    public void setComponentName(String componentName) {
        Validator.validate_componentName(componentName);
        this.componentName = Validator.getComponentName();
    }

    public void setComponentType(String componentType) {
        Validator.validate_componentType(componentType);
        this.componentType = Validator.getComponentType();
    }

    public void setComponentSpecs(String componentSpecs) {
        Validator.validate_componentSpecs(componentSpecs);
        this.componentSpecs = Validator.getComponentSpecs();
    }

    public void setComponentPrice(String componentPrice) {
        Validator.validate_componentPrice(componentPrice);
        this.componentPrice = Validator.getComponentPrice();
    }

    public int getComponentNumber() {
        return componentNumber;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getComponentSpecs() {
        return componentSpecs;
    }

    public double getComponentPrice() {
        return componentPrice;
    }
}
