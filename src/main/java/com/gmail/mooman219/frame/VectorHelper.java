package com.gmail.mooman219.frame;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
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

    public static Vector calculateLookVector(Location location, Location target) {
        return location.toVector().clone().subtract(target.toVector()).normalize();
    }

    /**
     * Power is sensitive, use low numbers like 0.0 - 1.5
     */
    public static void pushAwayFromPoint(Entity entity, Location point, double power) {
        pushAwayFromPoint(entity, point, power, new Vector(0,0,0));
    }

    /**
     * Power is sensitive, use low numbers like 0.0 - 1.5
     */
    public static void pushAwayFromPoint(Entity entity, Location point, double power, Vector adjustment) {
        entity.setVelocity(entity.getVelocity().add(calculateLookVector(entity.getLocation(), point).add(adjustment).multiply(power)));
    }

    /**
     * Power is sensitive, use low numbers like 0.0 - 1.5
     */
    public static void pushBackwards(Entity entity, double power) {
        pushBackwards(entity, power, new Vector(0,0,0));
    }

    /**
     * Power is sensitive, use low numbers like 0.0 - 1.5
     */
    public static void pushBackwards(Entity entity, double power, Vector adjustment) {
        entity.setVelocity(entity.getVelocity().add(calculateLookVector(entity.getLocation()).add(adjustment).multiply(power)));
    }
}
