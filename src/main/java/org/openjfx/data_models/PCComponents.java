package org.openjfx.data_models;

public class PCComponents {

    private String componentName;
    private String componentDesc;
    private String componentType;
    private int componentPrice;

    public PCComponents(String componentName, String componentDesc, String componentType, String componentPrice){
        this.componentName = componentName;
        this.componentDesc = componentDesc;
        this.componentType = componentType;
        this.componentPrice = Integer.parseInt(componentPrice);
    }

    public String toString(){
        return String.format("%s;%s;%s;%s\n", componentName, componentDesc, componentType, componentPrice);
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public void setComponentDesc(String componentDesc) {
        this.componentDesc = componentDesc;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public void setComponentPrice(int componentPrice) {
        this.componentPrice = componentPrice;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getComponentDesc() {
        return componentDesc;
    }

    public String getComponentType() {
        return componentType;
    }

    public int getComponentPrice() {
        return componentPrice;
    }
}
