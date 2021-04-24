package org.openjfx.dataModels;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PCConfigurations implements Serializable {

    private static AtomicLong PCConfigurationID = new AtomicLong();
    private ArrayList<PCComponents> pcComponents;
    private final double totalPrice;

    public PCConfigurations(AtomicLong pcConfigurationID, ArrayList<PCComponents> pcComponents, double totalPrice){
        PCConfigurationID = pcConfigurationID;
        this.pcComponents = pcComponents;
        this.totalPrice = totalPrice;
    }

    public static String createID() {
        return String.valueOf(PCConfigurationID.getAndIncrement());
    }

    public static double calculateTotalPrice(ArrayList<PCComponents> pcComponents){
        double totalPrice = 0;
        for(PCComponents component : pcComponents){
            totalPrice += component.getComponentPrice();
        }
        return totalPrice;
    }

    public String toString() {
        PCConfigurations configuration = new PCConfigurations(PCConfigurationID, pcComponents, totalPrice);
        Gson gson = new Gson();
        String objectStr = gson.toJson(configuration);
        return String.format("%s\n", objectStr);
    }

    public void setPcComponents(ArrayList<PCComponents> pcComponents) {
        this.pcComponents = pcComponents;
    }

    public ArrayList<PCComponents> getPcComponents() {
        return pcComponents;
    }
}
