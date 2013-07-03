package com.gmail.mooman219.bull;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;

import com.gmail.mooman219.craftbukkit.BullData;

public class CDWorld extends BullData {
    private final World world;

    private CDWorld(World world) {
        this.world = world;
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

    public World getWorld() {
        return world;
    }

    public net.minecraft.server.World getHandle() {
        return ((CraftWorld)world).getHandle();
    }

    public static CDWorld get(Entity entity) {
        return get(entity.getLocation().getWorld());
    }

    public static CDWorld get(World world) {
        net.minecraft.server.World handle = ((CraftWorld)world).getHandle();
        if(handle.bull_live == null) {
            handle.bull_live = new CDWorld(world);
        }
        return (CDWorld) handle.bull_live;
    }
}