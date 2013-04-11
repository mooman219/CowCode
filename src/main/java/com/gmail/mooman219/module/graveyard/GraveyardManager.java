package com.gmail.mooman219.module.graveyard;

import java.util.Iterator;

import org.bukkit.Location;

import com.gmail.mooman219.frame.LocationHelper;
import com.gmail.mooman219.frame.serialize.CSLocation;
import com.gmail.mooman219.module.graveyard.serialize.CSGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class GraveyardManager {
    // returns true if the graveyarg already existed
    public static boolean addGraveyard(Location location, int levelRequirement) {
        CSLocation loc = new CSLocation(location);
        for(CSGraveyard graveyardData : StoreGraveyard.graveyards) {
            if(graveyardData.location.equals(loc)) {
                graveyardData.levelRequirement = levelRequirement;
                return true;
            }
        }
        StoreGraveyard.graveyards.add(new CSGraveyard(location, levelRequirement));
        return false;
    }

    // returns the graveyard matching the location
    public static CSGraveyard getGraveyard(int id) {
        if(StoreGraveyard.graveyards.size() > id) {
            return StoreGraveyard.graveyards.get(id);
        }
        return null;
    }

    // returns the removed graveyard
    public static CSGraveyard removeGraveyard(Location location) {
        CSGraveyard closestGraveyard = getClosestGraveyard(location);
        Iterator<CSGraveyard> iterator = StoreGraveyard.graveyards.iterator();
        while(iterator.hasNext()) {
            CSGraveyard graveyardData = iterator.next();
            if(graveyardData.location.equals(closestGraveyard.location)) {
                iterator.remove();
                return closestGraveyard;
            }
        }
        return null;
    }

    // returns the closest graveyard
    public static CSGraveyard getClosestGraveyard(Location location) {
        if(StoreGraveyard.graveyards.size() <= 0) {
            return null;
        }
        CSGraveyard best = StoreGraveyard.graveyards.get(0);
        double lowestDistance = LocationHelper.get2DistanceSquared(StoreGraveyard.graveyards.get(0).getLocation(), location);
        for(CSGraveyard graveyardData : StoreGraveyard.graveyards) {
            double possibleDistance = LocationHelper.get2DistanceSquared(graveyardData.getLocation(), location);
            if(possibleDistance < lowestDistance) {
                lowestDistance = possibleDistance;
                best = graveyardData;
            }
        }
        return best;
    }
}
