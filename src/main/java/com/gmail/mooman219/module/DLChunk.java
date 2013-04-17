package com.gmail.mooman219.module;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.Chunk;
import org.bukkit.craftbukkit.CraftChunk;

import com.gmail.mooman219.frame.TagHelper;

public class DLChunk {
    public final Chunk chunk;

    public DLChunk(Chunk chunk) {
        this.chunk = chunk;
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
    
    public net.minecraft.server.Chunk getHandle() {
        return ((CraftChunk)chunk).getHandle();
    }

    public static DLChunk get(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.dataLive == null) {
            handle.dataLive = new DLChunk(chunk);
        } else if(!(handle.dataLive instanceof DLChunk)) {
            throw new IllegalArgumentException("Invalid data on chunk.");
        }
        return (DLChunk) handle.dataLive;
    }
}
