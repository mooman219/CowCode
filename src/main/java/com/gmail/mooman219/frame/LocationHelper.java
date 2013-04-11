package com.gmail.mooman219.frame;

import org.bukkit.Location;

public class LocationHelper {
    public static double get2DistanceSquared(Location a, Location b) {
        return Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getZ() - b.getZ(), 2);
    }

    public static double get2Distance(Location a, Location b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }

    public static double get3DistanceSquared(Location a, Location b) {
        return Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2);
    }

    public static double get3Distance(Location a, Location b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }
}
