package com.gmail.mooman219.module.mineral;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.serialize.jack.FastLocation;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.module.mineral.store.FastMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class MineralManager {
    private static ArrayList<FastMineral> active = new ArrayList<FastMineral>();

    public static FastMineral getMineral(Location location) {
        return StoreMineral.getMinerals().get(new FastLocation(location));
    }

    /**
     * @return The number of minerals removed.
     */
    public static int clear() {
        revert();
        int ret = StoreMineral.getMinerals().size();
        StoreMineral.getMinerals().clear();
        return ret;
    }

    /**
     * @return True if mineral already existed.
     */
    public static boolean add(FastMineral mineral) {
        boolean replace = StoreMineral.getMinerals().remove(mineral.getLocation()) != null;
        StoreMineral.getMinerals().put(mineral.getLocation(), mineral);
        return replace;
    }

    /**
     * @return True if mineral was removed.
     */
    public static boolean remove(FastLocation location) {
        return StoreMineral.getMinerals().remove(location) != null;
    }

    public static void mine(FastMineral mineral) {
        active.remove(mineral);
        Location location = mineral.toLocation();
        location.getBlock().setType(Material.COBBLESTONE);
        WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
        mineral.resetTime(TimeHelper.time());
        active.add(mineral);
    }

    public static void tick() {
        long time = TimeHelper.time();
        Iterator<FastMineral> iterator = active.iterator();
        while(iterator.hasNext()) {
            FastMineral mineral = iterator.next();
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
        Iterator<FastMineral> iterator = StoreMineral.getMinerals().values().iterator();
        while(iterator.hasNext()) {
            FastMineral mineral = iterator.next();
            revert(mineral);
        }
    }

    /**
     * Reverts the given mineral if it is valid
     */
    public static void revert(FastMineral mineral) {
        active.remove(mineral);
        Location location = mineral.toLocation();
        location.getBlock().setType(mineral.getType());
        WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
        mineral.setRespawnTime(0L);
    }
}
