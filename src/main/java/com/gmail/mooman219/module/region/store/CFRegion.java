package com.gmail.mooman219.module.region.store;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.file.ConfigBase;
import com.gmail.mooman219.frame.serialize.CSChunkLocation;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.serialize.CSRegion;

public class CFRegion extends ConfigBase {
    public static HashMap<CSChunkLocation, CSRegion> regions = new HashMap<CSChunkLocation, CSRegion>();

    public CFRegion() {
        super(CCRegion.directory, "region.data.yml");
        super.init();
    }

    @Override
    public void onLoad() {
        regions = new HashMap<CSChunkLocation, CSRegion>();
        for(CSRegion data : loadVar("Regions", new ArrayList<CSRegion>())) {
            regions.put(data.chunk, data);
        }
    }

    @Override
    public void onSave() {
        ArrayList<CSRegion> regions = new ArrayList<CSRegion>();
        regions.addAll(CFRegion.regions.values());
        saveVar("Regions", regions);
    }
    
    public static CSRegion getRegion(Player player) {
        return getRegion(player.getLocation().getChunk());
    }

    public static CSRegion getRegion(Chunk chunk) {
        CSChunkLocation csChunkLocation = new CSChunkLocation(chunk);
        if(regions.containsKey(csChunkLocation)) {
            return regions.get(csChunkLocation);
        } else {
            CSRegion csRegion = new CSRegion(CFInfo.global.uuid, chunk);
            csRegion.setCSRegionInformation(CFInfo.global);
            regions.put(csChunkLocation, csRegion);
            return csRegion;
        }
    }
}

