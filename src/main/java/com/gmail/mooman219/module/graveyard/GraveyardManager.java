package com.gmail.mooman219.module.graveyard;

import java.util.Iterator;

import org.bukkit.Location;

import com.gmail.mooman219.frame.LocationHelper;
import com.gmail.mooman219.module.graveyard.store.FastGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class GraveyardManager {
    /**
     * @return If the graveyard already existed.
     */
    public static boolean addGraveyard(Location location, int levelRequirement) {
        FastGraveyard loc = new FastGraveyard(location, levelRequirement);
        boolean ret = false;
        Iterator<FastGraveyard> iterator = StoreGraveyard.getGraveyards().iterator();
        while(iterator.hasNext()) {
            FastGraveyard graveyardData = iterator.next();
            if(graveyardData.getLocation().equals(loc.getLocation())) {
                iterator.remove();
                ret = true;
                // I am not breaking because if BY CHANCE extra graveyards got into the mix that shouldn't have.
            }
        }
        StoreGraveyard.getGraveyards().add(new FastGraveyard(location, levelRequirement));
        return ret;
    }

    /**
     * @return The graveyard with the matching id.
     */
    public static FastGraveyard getGraveyard(int id) {
        if(StoreGraveyard.getGraveyards().size() > id) {
            return StoreGraveyard.getGraveyards().get(id);
        }
        return null;
    }

    /**
     * @return The removed graveyard
     */
    public static FastGraveyard removeGraveyard(Location location) {
        FastGraveyard closestGraveyard = getClosestGraveyard(location);
        Iterator<FastGraveyard> iterator = StoreGraveyard.getGraveyards().iterator();
        while(iterator.hasNext()) {
            FastGraveyard graveyardData = iterator.next();
            if(graveyardData.getLocation().equals(closestGraveyard.getLocation())) {
                iterator.remove();
                return closestGraveyard;
            }
        }
        return null;
    }

    /**
     * @return The closest graveyard.
     */
    public static FastGraveyard getClosestGraveyard(Location location) {
        if(StoreGraveyard.getGraveyards().size() <= 0) {
            return null;
        }
        FastGraveyard best = StoreGraveyard.getGraveyards().get(0);
        double lowestDistance = LocationHelper.get2DistanceSquared(StoreGraveyard.getGraveyards().get(0).toLocation(), location);
        for(FastGraveyard graveyardData : StoreGraveyard.getGraveyards()) {
            double possibleDistance = LocationHelper.get2DistanceSquared(graveyardData.toLocation(), location);
            if(possibleDistance < lowestDistance) {
                lowestDistance = possibleDistance;
                best = graveyardData;
            }
        }
        return best;
    }
}
