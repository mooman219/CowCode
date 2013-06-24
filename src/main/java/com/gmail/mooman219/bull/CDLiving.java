package com.gmail.mooman219.bull;

import net.minecraft.server.EntityLiving;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.craftbukkit.BullData;

public class CDLiving extends BullData {
    public final LivingEntity livingEntity;

    private CDLiving(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
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
            handle.bull_live = new CDLiving(livingEntity);
        }
        return (CDLiving) handle.bull_live;
    }
    
    public static void unload(LivingEntity livingEntity) {
        net.minecraft.server.Entity handle = ((CraftLivingEntity)livingEntity).getHandle();
        if(livingEntity instanceof Player) {
            throw new IllegalArgumentException("Players are not considered LivingEntities.");
        } else if(handle.bull_live != null) {
            if(handle.bull_live instanceof BullData) {                
                ((BullData) handle.bull_live).onTagSave(handle.bull_tag);
            }
            handle.bull_live = null;
        }
    }
}
