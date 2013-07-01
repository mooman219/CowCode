package com.gmail.mooman219.module.graveyard;

import java.util.Iterator;

import org.bukkit.Location;

import com.gmail.mooman219.frame.LocationHelper;
import com.gmail.mooman219.module.graveyard.store.BasicGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class GraveyardManager {
    /**
     * @return If the graveyard already existed.
     */
    public static boolean addGraveyard(Location location, int levelRequirement) {
        BasicGraveyard loc = new BasicGraveyard(location, levelRequirement);
        boolean ret = false;
        Iterator<BasicGraveyard> iterator = StoreGraveyard.getGraveyards().iterator();
        while(iterator.hasNext()) {
            BasicGraveyard graveyardData = iterator.next();
            if(graveyardData.getBasicLocation().equals(loc.getBasicLocation())) {
                iterator.remove();
                ret = true;
                break;
            }
        }
        StoreGraveyard.getGraveyards().add(new BasicGraveyard(location, levelRequirement));
        return ret;
    }

    /**
     * @return The graveyard with the matching id.
     */
    public static BasicGraveyard getGraveyard(int id) {
        if(StoreGraveyard.getGraveyards().size() > id) {
            return StoreGraveyard.getGraveyards().get(id);
        }
        return null;
    }

    /**
     * @return The removed graveyard
     */
    public static BasicGraveyard removeGraveyard(Location location) {
        BasicGraveyard closestGraveyard = getClosestGraveyard(location);
        Iterator<BasicGraveyard> iterator = StoreGraveyard.getGraveyards().iterator();
        while(iterator.hasNext()) {
            BasicGraveyard graveyardData = iterator.next();
            if(graveyardData.getBasicLocation().equals(closestGraveyard.getBasicLocation())) {
                iterator.remove();
                return closestGraveyard;
            }
        }
        return null;
    }

    /**
     * @return The closest graveyard.
     */
    public static BasicGraveyard getClosestGraveyard(Location location) {
        if(StoreGraveyard.getGraveyards().size() <= 0) {
            return null;
        }
        BasicGraveyard best = StoreGraveyard.getGraveyards().get(0);
        double lowestDistance = LocationHelper.get2DistanceSquared(StoreGraveyard.getGraveyards().get(0).getLocation(), location);
        for(BasicGraveyard graveyardData : StoreGraveyard.getGraveyards()) {
            double possibleDistance = LocationHelper.get2DistanceSquared(graveyardData.getLocation(), location);
            if(possibleDistance < lowestDistance) {
                lowestDistance = possibleDistance;
                best = graveyardData;
            }
        }
        return best;
    }
}
