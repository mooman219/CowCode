package com.gmail.mooman219.bull;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.gmail.mooman219.craftbukkit.BullData;

public class CDEntity extends BullData {
    private final Entity entity;

    private CDEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Variables
     */

    /*
     * Live
     */

    /*
     * Event
     */

    /*
     * Default
     */

    public Entity getEntity() {
        return entity;
    }

    public net.minecraft.server.Entity getHandle() {
        return ((CraftEntity)entity).getHandle();
    }

    public static CDEntity get(Entity entity) {
        net.minecraft.server.Entity handle = ((CraftEntity)entity).getHandle();
        if(entity instanceof LivingEntity) {
            throw new IllegalArgumentException("LivingEntities are not considered Entities.");
        } else if(handle.bull_live == null) {
            handle.bull_live = new CDEntity(entity);
            handle.bull_live.onTagLoad(handle.bull_tag);
        }
        CDEntity cdEntity = (CDEntity) handle.bull_live;
        cdEntity.onGet();
        return cdEntity;
    }
}
