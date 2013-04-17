package com.gmail.mooman219.module;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldData;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

import com.gmail.mooman219.frame.TagHelper;

public class DLWorld {
    public final World world;
    
    public DLWorld(World world) {
        this.world = world;
        loadTag();
    }
    
    /*
     * Module
     */
    
    // None

    /*
     * Tag
     */
    
    private int test = 0;
    
    public void setTest(int test) {
    	this.test = test;
    	getHandle().dataTag.setInt("test", test);
    }
    
    public int getTest() {
    	return test;
    }
    
    public void loadTag() {
    	NBTTagCompound tag = getHandle().dataTag;
    	test = TagHelper.getInt(tag, "test", test);
    }
    
    /*
     * Default
     */
    
    public WorldData getHandle() {
        return ((CraftWorld)world).getHandle().getWorldData();
    }
    
    public static DLWorld get(World world) {
        WorldData handle = ((CraftWorld)world).getHandle().getWorldData();
        if(handle.dataLive == null) {
            handle.dataLive = new DLWorld(world);
        } else if(!(handle.dataLive instanceof DLWorld)) {
            throw new IllegalArgumentException("Invalid data on world.");
        }
        return (DLWorld) handle.dataLive;
    }
}