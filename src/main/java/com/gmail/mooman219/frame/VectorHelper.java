package com.gmail.mooman219.frame;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorHelper {
    public static Vector calculateLookVector(Location location) {
        double pitch = Math.toRadians(location.getPitch());
        double yaw = Math.toRadians(location.getYaw());
        Vector normal = new Vector(
                -(Math.cos(pitch) * Math.sin(yaw)),
                -Math.sin(pitch),
                Math.cos(pitch) * Math.cos(yaw));
        return normal;
    }
}
