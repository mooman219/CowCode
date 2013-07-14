package com.gmail.mooman219.frame;

import org.bukkit.Location;

public class LocationHelper {
    public static double get2DistanceSquared(Location a, Location b) {
        if(!a.getWorld().equals(b.getWorld())) {
            return Double.MAX_VALUE;
        }
        return Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getZ() - b.getZ(), 2);
    }

    public static double get2Distance(Location a, Location b) {
        if(!a.getWorld().equals(b.getWorld())) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }

    public static double get3DistanceSquared(Location a, Location b) {
        if(!a.getWorld().equals(b.getWorld())) {
            return Double.MAX_VALUE;
        }
        return Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2);
    }

    public static double get3Distance(Location a, Location b) {
        if(!a.getWorld().equals(b.getWorld())) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }

    public static Location getBlockCenter(Location location) {
        location.setX(location.getBlockX() + 0.5D);
        location.setZ(location.getBlockZ() + 0.5D);
        return location;
    }
}
