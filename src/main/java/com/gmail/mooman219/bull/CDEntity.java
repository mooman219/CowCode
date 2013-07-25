package com.gmail.mooman219.bull;

import org.bukkit.craftbukkit.v1_6_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.gmail.mooman219.craftbukkit.BullData;

public class CDEntity extends BullData {
    private final Entity entity;

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
     * BullData
     */

    public Entity getEntity() {
        return entity;
    }

    public net.minecraft.server.v1_6_R2.Entity getHandle() {
        return ((CraftEntity)entity).getHandle();
    }

    public static CDEntity get(Entity entity) {
        net.minecraft.server.v1_6_R2.Entity handle = ((CraftEntity)entity).getHandle();
        if(entity instanceof LivingEntity) {
            throw new IllegalArgumentException("LivingEntities are not considered Entities.");
        } else if(handle.bull_live == null) {
            handle.bull_live = new CDEntity(entity);
        }
        CDEntity cdEntity = (CDEntity) handle.bull_live;
        cdEntity.onGet();
        return cdEntity;
    }
}
