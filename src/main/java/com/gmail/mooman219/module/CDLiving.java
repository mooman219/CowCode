package com.gmail.mooman219.module;

import net.minecraft.server.EntityLiving;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;

public class CDLiving implements CowData {
    public final LivingEntity livingEntity;

    private CDLiving(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
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

    public EntityLiving getHandle() {
        return ((CraftLivingEntity)livingEntity).getHandle();
    }

    public static CDLiving get(LivingEntity livingEntity) {
        net.minecraft.server.Entity handle = ((CraftLivingEntity)livingEntity).getHandle();
        if(livingEntity instanceof Player) {
            throw new IllegalArgumentException("Players are not considered LivingEntities.");
        } else if(handle.dataLive == null) {
            handle.dataLive = new CDLiving(livingEntity);
        }
        return (CDLiving) handle.dataLive;
    }
}
