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
import com.gmail.mooman219.handler.config.ConfigGlobal;
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
    private long lastActive = Long.MAX_VALUE;
    // Saved
    public String parentUUID = "";
    public ArrayList<Mineral> minerals = new ArrayList<Mineral>();

    public void tick() {
        long time = System.currentTimeMillis();
        tick = 0;
        // Minerals!
        for(Mineral mineral : minerals) {
            mineral.tick(chunk, time);
        }
        // Chunk unloading stuff
        if(time - lastActive > ConfigGlobal.bull.chunk.chunkUnloadDelay) {
            CDChunk.unload(chunk);
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

    public void onGet() {
        this.lastActive = System.currentTimeMillis();
    }

    @Override
    public void onTick() {
        if(tick >= ConfigGlobal.bull.chunk.chunkTickPeriod) {
            tick();
        }
        tick++;
    }

    @Override
    public void onTagLoad(NBTTagCompound tag) {
        parentUUID = TagHelper.getString(tag, "region.uuid", parentUUID);
        minerals = Mineral.fromTagList(TagHelper.getCompound(tag, "mineral.list", new NBTTagCompound()));
    }

    @Override
    public void onTagSave(NBTTagCompound tag) {
        tag.setString("region.uuid", parentUUID);
        tag.setCompound("mineral.list", Mineral.toTagList(minerals));

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
        CDChunk cdChunk = (CDChunk) handle.bull_live;
        cdChunk.onGet();
        return cdChunk;
    }

    /**
     * This method will save any of the CDChunk's data then remove it from the chunk object.
     * This should be called when the chunk unloads to prevent the data from presisting.
     */
    public static void unload(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.bull_live != null) {
            handle.bull_live.onTagSave(handle.bull_tag);
            handle.bull_live = null;
        }
    }
}
