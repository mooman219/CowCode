package com.gmail.mooman219.module;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;

import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityController {
    private static final Location AT_LOCATION = new Location(null, 0, 0, 0);
    private static final Location FROM_LOCATION = new Location(null, 0, 0, 0);
    
    private LivingEntity livingEntity;
    
    public EntityController(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }
    
    public EntityLiving getHandle() {
        return ((CraftLivingEntity)livingEntity).getHandle();
    }
    
    public void face(Entity entity) {
        Location atLocation = entity.getLocation(AT_LOCATION);
        Location fromLocation = livingEntity.getLocation(FROM_LOCATION);
        double xDiff, yDiff, zDiff;
        xDiff = atLocation.getX() - fromLocation.getX();
        yDiff = atLocation.getY() - fromLocation.getY();
        zDiff = atLocation.getZ() - fromLocation.getZ();

        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);

        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
        double pitch = Math.toDegrees(Math.acos(yDiff / distanceY)) - 90;
        if (zDiff < 0.0)
            yaw += Math.abs(180 - yaw) * 2;
        
        look((float)yaw - 90F, (float)pitch);
    }
    
    public void look(float yaw, float pitch) {
        EntityLiving handle = getHandle();
        handle.yaw = yaw;
        setHeadYaw(yaw);
        handle.pitch = pitch;
    }
    
    public void setHeadYaw(float yaw) {
        EntityLiving handle = getHandle();
        while (yaw < -180.0F) {
            yaw += 360.0F;
        }

        while (yaw >= 180.0F) {
            yaw -= 360.0F;
        }
        handle.aA = yaw;
        if (!(handle instanceof EntityHuman))
            handle.ay = yaw;
        handle.aB = yaw;
    }
}
