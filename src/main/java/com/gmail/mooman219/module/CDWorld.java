package com.gmail.mooman219.module;

import net.minecraft.server.WorldData;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;
import com.gmail.mooman219.frame.TagHelper;

public class CDWorld implements CowData {
    public final World world;

    public CDWorld(World world) {
        this.world = world;
        onLoad(getHandle());
    }

    /*
     * Live
     */

    @Override
    public void onTick(CowTaggable handle) {}

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

    public WorldData getHandle() {
        return ((CraftWorld)world).getHandle().getWorldData();
    }
    
    public static CDWorld get(Entity entity) {
        return get(entity.getLocation().getWorld());
    }

    public static CDWorld get(World world) {
        WorldData handle = ((CraftWorld)world).getHandle().getWorldData();
        if(handle.dataLive == null) {
            handle.dataLive = new CDWorld(world);
        }
        // It should always be a CDWorld
        /**else if(!(handle.dataLive instanceof CDWorld)) {
            throw new IllegalArgumentException("Invalid data on world.");
        }**/
        return (CDWorld) handle.dataLive;
    }
}