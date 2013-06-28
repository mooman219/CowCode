package com.gmail.mooman219.bull;

import java.util.ArrayList;

import com.gmail.mooman219.module.mineral.store.Mineral;

public class CDChunkData {
    /**
     * Eveything in this class will be saved when the chunk is saved.
     * Everything will also be loaded when the chunk is loaded.
     */
    public String parentUUID = "";
    public ArrayList<Mineral> minerals = new ArrayList<Mineral>();
}
