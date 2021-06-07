package org.openjfx.guiUtilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;

public class JsonBeautify {
    public static String beautify(ArrayList<Object> data){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(data);
    }
}
