package com.gmail.mooman219.bull;

import java.lang.ref.SoftReference;
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
import com.gmail.mooman219.module.region.store.CSRegionInfo;
import com.gmail.mooman219.module.region.store.StoreRegionInfo;

public class CDChunk extends BullData {
    // [+] Data information
    public final Chunk chunk;

    public CDChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /*
     * Live
     */

    // Unsaved
    private SoftReference<CSRegionInfo> softParentInfo;
    private byte tick = 0;
    // Saved
    public String parentUUID = "";
    public ArrayList<Mineral> minerals = new ArrayList<Mineral>();

    public void tick() {
        long time = System.currentTimeMillis();
        tick = 0;
        for(Mineral mineral : minerals) {
            mineral.tick(chunk, time);
        }
    }
    
    public CSRegionInfo getParentInfo() {
        if(softParentInfo == null || softParentInfo.get() == null) {
            softParentInfo = new SoftReference<CSRegionInfo>(StoreRegionInfo.getInfo(parentUUID));
        }
        return softParentInfo.get();
    }

    public void setParentInformation(CSRegionInfo info) {
        softParentInfo = new SoftReference<CSRegionInfo>(info);
        parentUUID = info.getUUID();
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

    @Override
    public void onTick() {
        // Tick the minerals every 1.5 seconds because the system is based on REAL time.
        if(tick >= 30) {
            tick();
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

    public static CDChunk get(Location location) {
        return get(location.getChunk());
    }

    public static CDChunk get(Entity entity) {
        return get(entity.getLocation().getChunk());
    }

    public static CDChunk get(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.bull_live == null) {
            handle.bull_live = new CDChunk(chunk);
            handle.bull_live.onTagLoad(handle.bull_tag);
        }
        return (CDChunk) handle.bull_live;
    }

    /**
     * This method will save any of the CDChunk's data then remove it from the chunk object.
     * This should be called when the chunk unloads to prevent the data from presisting.
     */
    public static void unload(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.bull_live != null) {
            if(handle.bull_live instanceof BullData) {                
                ((BullData) handle.bull_live).onTagSave(handle.bull_tag);
            }
            handle.bull_live = null;
        }
    }
}
