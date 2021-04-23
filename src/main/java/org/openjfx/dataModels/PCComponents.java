package org.openjfx.dataModels;

import com.google.gson.Gson;
import org.openjfx.dataCollection.ComponentsCollection;
import org.openjfx.dataValidator.Validator;

/** Model of Pc components. */

public class PCComponents {

    private int PCComponentID;
    private String componentName;
    private String componentType;
    private String componentSpecs;
    private double componentPrice;

    public PCComponents(int PCComponentID, String componentName, String componentType, String componentSpecs, String componentPrice){
        Validator.validateComponentName(componentName);
        Validator.validateComponentSpecs(componentSpecs);
        Validator.validateComponentType(componentType);
        Validator.validateComponentPrice(componentPrice);

        this.PCComponentID = PCComponentID;
        this.componentName = Validator.getComponentName();
        this.componentType = Validator.getComponentType();
        this.componentSpecs = Validator.getComponentSpecs();
        this.componentPrice = Validator.getComponentPrice();
    }

    public String toString(){
        PCComponents p = new PCComponents(PCComponentID, componentName, componentType, componentSpecs, Double.toString(componentPrice));
        Gson gson = new Gson();
        String objectStr = gson.toJson(p);
        return String.format("%s\n", objectStr);
    }

    public static int createUniqueId(){
        int prevId = ComponentsCollection.getComponentObsList().size();
        return prevId + 1;
    }

    public static String getClassReference(){
        // Must be unique (id name)
        return "PCComponentID";
    }

    public void setPCComponentID(String PCComponentID) {
        Validator.validateComponentNumber(PCComponentID);
        this.PCComponentID = Validator.getPCComponentID();
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

    public int getPCComponentID() {
        return PCComponentID;
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
