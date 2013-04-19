package com.gmail.mooman219.module;

import net.minecraft.server.WorldData;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

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

    // None

    /*
     * Tag
     */

    public int test = 0;
    
    @Override
    public void onLoad(CowTaggable handle) {
        test = TagHelper.getInt(handle.dataTag, "test", test);
    }
    
    @Override
    public void onSave(CowTaggable handle) {
        handle.clearStoreTag();
        handle.dataTag.setInt("test", test);
    }

    /*
     * Default
     */

    public WorldData getHandle() {
        return ((CraftWorld)world).getHandle().getWorldData();
    }

    public static CDWorld get(World world) {
        WorldData handle = ((CraftWorld)world).getHandle().getWorldData();
        if(handle.dataLive == null) {
            handle.dataLive = new CDWorld(world);
        } else if(!(handle.dataLive instanceof CDWorld)) {
            throw new IllegalArgumentException("Invalid data on world.");
        }
        return (CDWorld) handle.dataLive;
    }
}