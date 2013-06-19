package com.gmail.mooman219.bullbukkit;

import java.util.ArrayList;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.entity.Entity;

import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.module.mineral.store.Mineral;
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
    
    public Mineral getMineral(Block block) {
        return getMineral(block.getLocation());
    }
    
    public Mineral getMineral(Location loc) {
        for(Mineral mineral : minerals) {
            if(mineral.match(loc)) {
                return mineral;
            }
        }
        return null;
    }

    /*
     * Event
     */

    public String parentUUID = "";
    public ArrayList<Mineral> minerals = new ArrayList<Mineral>();
    private byte tick = 0;

    @Override
    public void onTick() {
        if(tick >= 30) {
            long time = System.currentTimeMillis();
            tick = 0;
            for(Mineral mineral : minerals) {
                mineral.tick(chunk, time);
            }
        }
        tick++;
    }

    @Override
    public void onTagLoad(NBTTagCompound tag) {
        parentUUID = TagHelper.getString(tag, "region.uuid", parentUUID);
        minerals = Mineral.fromCompoundList(TagHelper.getCompound(tag, "mineral.list", new NBTTagCompound()));
    }

    @Override
    public void onTagSave(NBTTagCompound tag) {
        tag.setString("region.uuid", parentUUID);
        tag.setCompound("mineral.list", Mineral.toCompoundList(minerals));
    }

    /*
     * Default
     */

    public net.minecraft.server.Chunk getHandle() {
        return ((CraftChunk)chunk).getHandle();
    }

    public static CDChunk get(Block block) {
        return get(block.getChunk());
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
