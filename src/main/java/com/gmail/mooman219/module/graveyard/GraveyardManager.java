package com.gmail.mooman219.module.graveyard;

import java.util.Iterator;

import org.bukkit.Location;

import com.gmail.mooman219.frame.LocationHelper;
import com.gmail.mooman219.module.graveyard.store.BasicGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class GraveyardManager {
    // returns true if the graveyarg already existed
    public static boolean addGraveyard(Location location, int levelRequirement) {
        BasicGraveyard loc = new BasicGraveyard(location, levelRequirement);
        for(BasicGraveyard graveyardData : StoreGraveyard.getGraveyards()) {
            if(graveyardData.location.equals(loc)) {
                graveyardData.levelRequirement = levelRequirement;
                return true;
            }
        }
        StoreGraveyard.getGraveyards().add(new BasicGraveyard(location, levelRequirement));
        return false;
    }

    // returns the graveyard matching the location
    public static BasicGraveyard getGraveyard(int id) {
        if(StoreGraveyard.getGraveyards().size() > id) {
            return StoreGraveyard.getGraveyards().get(id);
        }
        return null;
    }

    // returns the removed graveyard
    public static BasicGraveyard removeGraveyard(Location location) {
        BasicGraveyard closestGraveyard = getClosestGraveyard(location);
        Iterator<BasicGraveyard> iterator = StoreGraveyard.getGraveyards().iterator();
        while(iterator.hasNext()) {
            BasicGraveyard graveyardData = iterator.next();
            if(graveyardData.location.equals(closestGraveyard.location)) {
                iterator.remove();
                return closestGraveyard;
            }
        }
        return null;
    }

    // returns the closest graveyard
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
