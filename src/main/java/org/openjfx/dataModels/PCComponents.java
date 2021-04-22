package org.openjfx.dataModels;

import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataValidator.Validator;

/** Model of Pc components. */

public class PCComponents {

    private int componentNumber;
    private String componentName;
    private String componentType;
    private String componentSpecs;
    private double componentPrice;

    public PCComponents(int componentNumber, String componentName, String componentType, String componentSpecs, String componentPrice){
        Validator.validateComponentName(componentName);
        Validator.validateComponentSpecs(componentSpecs);
        Validator.validateComponentType(componentType);
        Validator.validateComponentPrice(componentPrice);

        this.componentNumber = componentNumber;
        this.componentName = Validator.getComponentName();
        this.componentType = Validator.getComponentType();
        this.componentSpecs = Validator.getComponentSpecs();
        this.componentPrice = Validator.getComponentPrice();
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s\n", componentNumber, componentName, componentType, componentSpecs, componentPrice);
    }

    public static int createUniqueId(){
        int prevId = ComponentsCollection.getComponentObsList().size();
        return prevId + 1;
    }

    public void setComponentNumber(String componentNumber) {
        Validator.validateComponentNumber(componentNumber);
        this.componentNumber = Validator.getComponentNumber();
    }

    public void setComponentName(String componentName) {
        Validator.validateComponentName(componentName);
        this.componentName = Validator.getComponentName();
    }

    public void setComponentType(String componentType) {
        Validator.validateComponentType(componentType);
        this.componentType = Validator.getComponentType();
    }

    public void setComponentSpecs(String componentSpecs) {
        Validator.validateComponentSpecs(componentSpecs);
        this.componentSpecs = Validator.getComponentSpecs();
    }

    public void setComponentPrice(String componentPrice) {
        Validator.validateComponentPrice(componentPrice);
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
