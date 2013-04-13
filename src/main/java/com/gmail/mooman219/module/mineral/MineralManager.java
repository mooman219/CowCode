package com.gmail.mooman219.module.mineral;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.serialize.CSBasicLocation;
import com.gmail.mooman219.module.mineral.store.CSMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class MineralManager {
    public static ArrayList<CSMineral> activeMinerals = null;

    public static void start() {
        if(activeMinerals != null) {
            stop();
        }
        activeMinerals = new ArrayList<CSMineral>();
    }

    public static void stop() {
        revert();
        activeMinerals = null;
    }

    // returns true if the mineral already existed
    public static boolean addMineral(Location location, Material material, int respawnDelay) {
        CSBasicLocation basicLocation = new CSBasicLocation(location);
        if(StoreMineral.minerals.containsKey(basicLocation)) {
            CSMineral mineralData = StoreMineral.minerals.get(basicLocation);
            mineralData.type = material;
            mineralData.respawnDelay = respawnDelay;
            return true;
        } else {
            StoreMineral.minerals.put(basicLocation, new CSMineral(location, material, respawnDelay));
            return false;
        }
    }

    // returns the Mineral matching the location
    public static CSMineral getMineral(Location location) {
        CSBasicLocation basicLocation = new CSBasicLocation(location);
        if(StoreMineral.minerals.containsKey(basicLocation)) {
            return StoreMineral.minerals.get(basicLocation);
        } else {
            return null;
        }
    }

    // returns the removed mineral
    public static CSMineral removeMineral(Location location) {
        CSBasicLocation basicLocation = new CSBasicLocation(location);
        Iterator<CSBasicLocation> iterator = StoreMineral.minerals.keySet().iterator();
        while(iterator.hasNext()) {
            CSBasicLocation bloc = iterator.next();
            if(basicLocation.equals(bloc)) {
                CSMineral mineralData = StoreMineral.minerals.get(basicLocation);
                revert(mineralData);
                iterator.remove();
                return mineralData;
            }
        }
        return null;
    }

    public static void addActiveMineral(CSMineral mineralData) {
        for(CSMineral subMineralData : activeMinerals) {
            if(subMineralData.location.equals(mineralData.location)) {
                return;
            }
        }
        activeMinerals.add(mineralData);
    }

    public static void revert() {
        Iterator<CSMineral> iterator = activeMinerals.iterator();
        while(iterator.hasNext()) {
            revert(iterator.next());
            iterator.remove();
        }
        activeMinerals.clear();
    }

    public static void revert(CSMineral mineralData) {
        mineralData.getLocation().getBlock().setType(mineralData.type);
        mineralData.timeLeft = 0;
        WorldHelper.playEffect(mineralData.getLocation(), Effect.MOBSPAWNER_FLAMES);
    }

    public static void update() {
        Iterator<CSMineral> iterator = activeMinerals.iterator();
        while(iterator.hasNext()) {
            CSMineral mineralData = iterator.next();
            mineralData.timeLeft--;
            if(mineralData.timeLeft < 1) {
                revert(mineralData);
                iterator.remove();
            }
        }
    }
}
