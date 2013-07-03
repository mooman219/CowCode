package com.gmail.mooman219.module.mineral;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.module.mineral.store.BasicMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class MineralManager {
    private static ArrayList<BasicMineral> active = new ArrayList<BasicMineral>();

    public static BasicMineral getMineral(Location location) {
        for(BasicMineral mineral : StoreMineral.getMinerals()) {
            if(mineral.match(location)) {
                return mineral;
            }
        }
        return null;
    }
    
    public static void mine(BasicMineral mineral) {
        Location location = mineral.getLocation();
        location.getBlock().setType(Material.COBBLESTONE);
        WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
        mineral.resetTime(System.currentTimeMillis());
        active.add(mineral);
    }

    public static void tick() {
        long time = System.currentTimeMillis();
        Iterator<BasicMineral> iterator = active.iterator();
        while(iterator.hasNext()) {
            BasicMineral mineral = iterator.next();
            if(mineral.hasTimeExpired(time)) {
                iterator.remove();
                revert(mineral);
            }
        }
    }

    /**
     * Reverts all valid minerals
     */
    public static void revert() {
        active.clear();
        Iterator<BasicMineral> iterator = StoreMineral.getMinerals().iterator();
        while(iterator.hasNext()) {
            BasicMineral mineral = iterator.next();
            revert(mineral);
        }
    }

    /**
     * Reverts the given mineral if it is valid
     */
    public static void revert(BasicMineral mineral) {
        active.remove(mineral);
        Location location = mineral.getLocation();
        location.getBlock().setType(mineral.getType());
        WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
        mineral.setRespawnTime(0L);
    }
}
