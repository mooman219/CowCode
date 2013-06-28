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
        for(BasicGraveyard graveyardData : StoreGraveyard.graveyards) {
            if(graveyardData.location.equals(loc)) {
                graveyardData.levelRequirement = levelRequirement;
                return true;
            }
        }
        StoreGraveyard.graveyards.add(new BasicGraveyard(location, levelRequirement));
        return false;
    }

    // returns the graveyard matching the location
    public static BasicGraveyard getGraveyard(int id) {
        if(StoreGraveyard.graveyards.size() > id) {
            return StoreGraveyard.graveyards.get(id);
        }
        return null;
    }

    // returns the removed graveyard
    public static BasicGraveyard removeGraveyard(Location location) {
        BasicGraveyard closestGraveyard = getClosestGraveyard(location);
        Iterator<BasicGraveyard> iterator = StoreGraveyard.graveyards.iterator();
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
        if(StoreGraveyard.graveyards.size() <= 0) {
            return null;
        }
        BasicGraveyard best = StoreGraveyard.graveyards.get(0);
        double lowestDistance = LocationHelper.get2DistanceSquared(StoreGraveyard.graveyards.get(0).getLocation(), location);
        for(BasicGraveyard graveyardData : StoreGraveyard.graveyards) {
            double possibleDistance = LocationHelper.get2DistanceSquared(graveyardData.getLocation(), location);
            if(possibleDistance < lowestDistance) {
                lowestDistance = possibleDistance;
                best = graveyardData;
            }
        }
        return best;
    }
}
