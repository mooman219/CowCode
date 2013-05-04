package com.gmail.mooman219.module;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;
import com.gmail.mooman219.frame.TagHelper;

public class CDLiving implements CowData {
    public final Entity entity;

    private CDLiving(Entity entity) {
        this.entity = entity;
        onLoad(getHandle());
    }

    /*
     * Live
     */

    public void aiFace(Entity entity) {
        Location atLocation = at.getLocation(AT_LOCATION);
        Location fromLocation = from.getLocation(FROM_LOCATION);
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
        
        net.minecraft.server.Entity handle = getHandle();
        net.minecraft.server.Entity otherHandle = ((CraftEntity)entity).getHandle();
        handle.yaw = ((float) Math.atan((otherHandle.locX - handle.locX) / (otherHandle.locZ - handle.locZ))) % 360f;
    }
    
    @Override
    public void onTick(CowTaggable handle) {
        /**
        net.minecraft.server.Entity eHandle = (net.minecraft.server.Entity) handle;
        float rotation = eHandle.yaw * (float)Math.PI / 180f;
        eHandle.motZ = .1f * Math.cos(rotation);
        eHandle.motX = -.1f * Math.sin(rotation);
        eHandle.yaw = (eHandle.yaw + 1f) % 360f;
        **/
        /**
        Backwards
        eHandle.motZ = -.1f * Math.cos(rotation);
        eHandle.motX = .1f * Math.sin(rotation);
         */
    }

    /*
     * Tag
     */
    
    @Override
    public void onLoad(CowTaggable handle) {
    }
    
    @Override
    public void onSave(CowTaggable handle) {
        handle.clearStoreTag();
    }

    /*
     * Default
     */

    public net.minecraft.server.Entity getHandle() {
        return ((CraftEntity)entity).getHandle();
    }

    public static CDLiving get(LivingEntity livingEntity) {
        net.minecraft.server.Entity handle = ((CraftEntity)livingEntity).getHandle();
        if(livingEntity instanceof Player) {
            throw new IllegalArgumentException("Players are not considered LivingEntities.");
        } else if(handle.dataLive == null) {
            handle.dataLive = new CDLiving(livingEntity);
        }
        return (CDLiving) handle.dataLive;
    }
}
