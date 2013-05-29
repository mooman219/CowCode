package com.gmail.mooman219.bullbukkit;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;

import com.gmail.mooman219.craftbukkit.BullData;

public class CDWorld extends BullData {
    public final World world;

    public CDWorld(org.bukkit.World world) {
        this.world = world;
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

    public net.minecraft.server.World getHandle() {
        return ((CraftWorld)world).getHandle();
    }

    public static CDWorld get(Entity entity) {
        return get(entity.getLocation().getWorld());
    }

    public static CDWorld get(org.bukkit.World world) {
        net.minecraft.server.World handle = ((CraftWorld)world).getHandle();
        if(handle.bull_live == null) {
            handle.bull_live = new CDWorld(world);
            ((BullData) handle.bull_live).onTagLoad(handle.bull_tag);
        }
        return (CDWorld) handle.bull_live;
    }
}