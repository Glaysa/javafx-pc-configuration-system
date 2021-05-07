package org.openjfx.dataModels;

import com.google.gson.Gson;
import org.openjfx.dataCollection.ConfigurationCollection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class PCConfigurations implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final UUID uuid = UUID.randomUUID();
    private final int PCConfigurationID;
    private ArrayList<PCComponents> pcComponents;
    private final double totalPrice;
    private final String configurationName;

    public PCConfigurations(String configurationName, int pcConfigurationID, ArrayList<PCComponents> pcComponents, double totalPrice){
        PCConfigurationID = pcConfigurationID;
        this.pcComponents = pcComponents;
        this.totalPrice = totalPrice;
        this.configurationName = configurationName;
    }

    public String toString() {
        PCConfigurations object = new PCConfigurations(configurationName, PCConfigurationID, pcComponents, totalPrice);
        Gson gson = new Gson();
        String objectStr = gson.toJson(object);
        return String.format("%s\n", objectStr);
    }

    public static int createID() {
        int prevId = ConfigurationCollection.getConfigsArrayList().size();
        return prevId + 1;
    }

    public void setPcComponents(ArrayList<PCComponents> pcComponents) {
        this.pcComponents = pcComponents;
    }

    public ArrayList<PCComponents> getPcComponents() {
        return pcComponents;
    }

    public int getPCConfigurationID() {
        return PCConfigurationID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public static UUID getUUID(){
        return uuid;
    }
}
