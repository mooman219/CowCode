package com.gmail.mooman219.module.graveyard.store;

import java.util.ArrayList;

import com.gmail.mooman219.frame.file.ConfigBase;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.serialize.CSGraveyard;

public class StoreGraveyard extends ConfigBase{
    public static ArrayList<CSGraveyard> graveyards = new ArrayList<CSGraveyard>();

    public StoreGraveyard() {
        super(CCGraveyard.directory, "graveyard.data.yml");
        super.init();
    }

    @Override
    public void onLoad() {
        graveyards = loadVar("Graveyard", new ArrayList<CSGraveyard>());
    }

    @Override
    public void onSave() {
        saveVar("Graveyard", graveyards);
    }
}
