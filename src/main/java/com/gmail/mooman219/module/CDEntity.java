package com.gmail.mooman219.module;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.gmail.mooman219.craftbukkit.BullData;

public class CDEntity extends BullData {
    public final Entity entity;

    private CDEntity(Entity entity) {
        this.entity = entity;
    }

    /*
     * Live
     */

    /*
     * Event
     */

    /*
     * Default
     */

    public net.minecraft.server.Entity getHandle() {
        return ((CraftEntity)entity).getHandle();
    }

    public static CDEntity get(Entity entity) {
        net.minecraft.server.Entity handle = ((CraftEntity)entity).getHandle();
        if(entity instanceof LivingEntity) {
            throw new IllegalArgumentException("LivingEntities are not considered Entities.");
        } else if(handle.bull_live == null) {
            handle.bull_live = new CDEntity(entity);
        }
        return (CDEntity) handle.bull_live;
    }
}
