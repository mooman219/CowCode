package com.gmail.mooman219.entity;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityController {
    private final Entity entity;
    private final net.minecraft.server.Entity handle;
    private EntityGoal goal;
    
    public EntityController(Entity entity) {
        this.entity = entity;
        this.handle = getHandle();
    }
    
    public EntityLiving getHandle() {
        return ((CraftLivingEntity)entity).getHandle();
    }
    
    public void turn(Vector point) {
        Vector diff = point.subtract(entity.getLocation().toVector());
        float yaw = (float)(Math.toDegrees(Math.atan2(diff.getZ(), diff.getX())) + 270F);
        look(yaw, 0F);
    }
    
    public void face(Vector point) {
        Vector diff = point.subtract(entity.getLocation().toVector());
        double distanceXZ = Math.sqrt(diff.getX() * diff.getX() + diff.getZ() * diff.getZ());
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + diff.getY() * diff.getY());

        double yaw = Math.toDegrees(Math.acos(diff.getX() / distanceXZ));
        double pitch = Math.toDegrees(Math.acos(diff.getY() / distanceY)) - 90;
        if (diff.getZ() < 0.0)
            yaw += Math.abs(180 - yaw) * 2;
        
        look((float)yaw - 90F, (float)pitch);
    }
    
    public void look(float yaw, float pitch) {
        handle.yaw = yaw;
        handle.pitch = pitch;
        setHeadYaw(yaw);
    }

    public void setHeadYaw(float yaw) {
        if(handle instanceof EntityLiving) {
            EntityLiving handle = (EntityLiving)this.handle;
            yaw = ((yaw + 180F) % 360) - 180F;
            handle.aA = yaw;
            if (!(handle instanceof EntityHuman))
                handle.ay = yaw;
            handle.aB = yaw;
        }
    }
}
