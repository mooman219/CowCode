package com.gmail.mooman219.module.mineral.store;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Location;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.google.gson.Gson;

public class StoreMineral extends ConfigJson {
    private static ArrayList<BasicMineral> graveyards = new ArrayList<BasicMineral>();

    public StoreMineral(String directory) {
        super(directory, "minerals", "data");
    }

    @Override
    public void onSave(FileWriter writer) {
        getGson().toJson(this, writer);
    }

    @Override
    public void onLoad(FileReader reader) {
        getGson().fromJson(reader, StoreMineral.class);
        // Remove invalid minerals when they are loaded.
        int removed = 0;
        Iterator<BasicMineral> iterator = graveyards.iterator();
        while(iterator.hasNext()) {
            BasicMineral mineral = iterator.next();
            Location location = mineral.getLocation();
            if(location == null) {
                iterator.remove();
                removed++;
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

    public static ArrayList<BasicMineral> getMinerals() {
        return graveyards;
    }
}
