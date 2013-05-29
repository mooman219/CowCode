package com.gmail.mooman219.bullbukkit;

import net.minecraft.server.EntityLiving;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.craftbukkit.BullData;

public class CDLiving extends BullData {
    public final LivingEntity livingEntity;
    public final EntityLiving handle;

    private CDLiving(LivingEntity livingEntity, EntityLiving handle) {
        this.livingEntity = livingEntity;
        this.handle = handle;
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

    public EntityLiving getHandle() {
        return ((CraftLivingEntity)livingEntity).getHandle();
    }

    public static CDLiving get(LivingEntity livingEntity) {
        net.minecraft.server.Entity handle = ((CraftLivingEntity)livingEntity).getHandle();
        if(livingEntity instanceof Player) {
            throw new IllegalArgumentException("Players are not considered LivingEntities.");
        } else if(handle.bull_live == null) {
            handle.bull_live = new CDLiving(livingEntity, (EntityLiving) handle);
        }
        return (CDLiving) handle.bull_live;
    }
}
