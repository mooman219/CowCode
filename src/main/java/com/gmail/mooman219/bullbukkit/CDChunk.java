package com.gmail.mooman219.bullbukkit;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.Chunk;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.entity.Entity;

import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class CDChunk extends BullData {
    public final Chunk chunk;

    public CDChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /*
     * Live
     */

    private CSRegionInformation parentInformation;

    public CSRegionInformation getParentInformation() {
        if(parentInformation == null) {
            parentInformation = StoreRegionInformation.getInformation(parentUUID);
            parentUUID = parentInformation.uuid;
        }
        return parentInformation;
    }

    public void setParentInformation(CSRegionInformation information) {
        parentInformation = information;
        parentUUID = information.uuid;
    }

    /*
     * Event
     */

    public String parentUUID = "";

    @Override
    public void onTick() {
        
    }
    
    @Override
    public void onTagLoad(NBTTagCompound tag) {
        parentUUID = TagHelper.getString(tag, "region.uuid", parentUUID);
    }

    @Override
    public void onTagSave(NBTTagCompound tag) {
        tag.setString("region.uuid", parentUUID);
    }

    /*
     * Default
     */

    public net.minecraft.server.Chunk getHandle() {
        return ((CraftChunk)chunk).getHandle();
    }

    public static CDChunk get(Entity entity) {
        return get(entity.getLocation().getChunk());
    }

    public static CDChunk get(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.bull_live == null) {
            handle.bull_live = new CDChunk(chunk);
            ((BullData) handle.bull_live).onTagLoad(handle.bull_tag);
        }
        return (CDChunk) handle.bull_live;
    }
}
