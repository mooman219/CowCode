package com.gmail.mooman219.module.mineral.file;

import java.util.ArrayList;
import java.util.HashMap;

import com.gmail.mooman219.frame.file.ConfigBase;
import com.gmail.mooman219.frame.serialize.CSBasicLocation;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.serialize.CSMineral;

public class StoreMineral extends ConfigBase {
    public static HashMap<CSBasicLocation, CSMineral> minerals = new HashMap<CSBasicLocation, CSMineral>();

    public StoreMineral() {
        super(CCMineral.directory, "mineral.data.yml");
        super.init();
    }

    @Override
    public void onLoad() {
        minerals = new HashMap<CSBasicLocation, CSMineral>();
        for(CSMineral data : loadVar("Mineral", new ArrayList<CSMineral>())) {
            minerals.put(data.location, data);
        }
    }

    @Override
    public void onSave() {
        ArrayList<CSMineral> minerals = new ArrayList<CSMineral>();
        minerals.addAll(StoreMineral.minerals.values());
        saveVar("Mineral", minerals);
    }
}
