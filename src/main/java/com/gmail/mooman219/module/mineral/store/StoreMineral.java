package com.gmail.mooman219.module.mineral.store;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.BasicLocation;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.google.gson.Gson;

public class StoreMineral extends ConfigJson {
    private static ArrayList<BasicMineral> minerals = new ArrayList<BasicMineral>();
    private static transient HashMap<BasicLocation, BasicMineral> mineralMap = new HashMap<BasicLocation, BasicMineral>();

    public StoreMineral(String cast, String directory) {
        super(cast, directory, "minerals", "data");
    }
    
    WORK ON MINERALS
    

    @Override
    public void onSave(FileWriter writer) {
        minerals.clear();
        for(BasicMineral mineral : mineralMap.values()) {
            minerals.add(mineral);
        }
        getGson().toJson(this, writer);
    }

    @Override
    public void onLoad(FileReader reader) {
        mineralMap.clear();
        getGson().fromJson(reader, StoreMineral.class);
        int removed = 0;
        Iterator<BasicMineral> iterator = minerals.iterator();
        while(iterator.hasNext()) {
            BasicMineral mineral = iterator.next();
            if(mineral.getLocation() == null) {
                iterator.remove();
                removed++;
            } else {
                mineralMap.put(mineral.getBasicLocation(), mineral);
            }
        }
        if(removed > 0) {
            Loader.warning(CCMineral.cast + "Removed " + removed + " invalid minerals.");
        }
    }

    @Override
    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .create();
    }

    public static HashMap<BasicLocation, BasicMineral> getMinerals() {
        return mineralMap;
    }
}
