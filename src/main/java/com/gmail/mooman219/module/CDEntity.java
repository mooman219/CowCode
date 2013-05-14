package com.gmail.mooman219.module;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;

public class CDEntity implements CowData {
    public final Entity entity;

    private CDEntity(Entity entity) {
        this.entity = entity;
        onLoad(getHandle());
    }

    /*
     * Live
     */

    /*
     * Tag
     */

    @Override
    public void onTick(CowTaggable handle) {}

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

    public static CDEntity get(Entity entity) {
        net.minecraft.server.Entity handle = ((CraftEntity)entity).getHandle();
        if(entity instanceof LivingEntity) {
            throw new IllegalArgumentException("LivingEntities are not considered Entities.");
        } else if(handle.dataLive == null) {
            handle.dataLive = new CDEntity(entity);
        }
        return (CDEntity) handle.dataLive;
    }
}
