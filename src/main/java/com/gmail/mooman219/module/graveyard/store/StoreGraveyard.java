package com.gmail.mooman219.module.graveyard.store;

import java.util.ArrayList;

import com.gmail.mooman219.frame.file.ConfigYaml;
import com.gmail.mooman219.module.graveyard.CCGraveyard;

public class StoreGraveyard extends ConfigYaml {
    public static ArrayList<CSGraveyard> graveyards = new ArrayList<CSGraveyard>();

    public StoreGraveyard() {
        super(CCGraveyard.directory, "graveyard.data.yml");
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
