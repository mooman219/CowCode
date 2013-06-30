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
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.mineral.store.Mineral;
import com.gmail.mooman219.module.region.store.BasicRegionInfo;
import com.gmail.mooman219.module.region.store.StoreRegionInfo;

public class CDChunk extends BullData {
    private final Chunk chunk;

    private CDChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Variables
     */

    // Saved
    private CDChunkData data = new CDChunkData();
    private static class CDChunkData {
        public String parentUUID = "";
        public ArrayList<Mineral> minerals = new ArrayList<Mineral>();
    }
    // Unsaved
    private SoftReference<BasicRegionInfo> softParentInfo;
    private byte tick = 0;
    private long lastActive = Long.MAX_VALUE;

    /*
     * Live
     */

    public Mineral getMineral(Block block) {
        return getMineral(block.getLocation());
    }

    public Mineral getMineral(Location loc) {
        for(Mineral mineral : data.minerals) {
            if(mineral.match(loc)) {
                return mineral;
            }
        }
        return null;
    }

    public ArrayList<Mineral> getMinerals() {
        return data.minerals;
    }

    public BasicRegionInfo getParentInfo() {
        if(softParentInfo == null || softParentInfo.get() == null) {
            setParentInformation(StoreRegionInfo.getInfo(data.parentUUID));
        }
        return softParentInfo.get();
    }

    public void setParentInformation(BasicRegionInfo info) {
        softParentInfo = new SoftReference<BasicRegionInfo>(info);
        data.parentUUID = info.getUUID();
    }

    public void tick() {
        long time = System.currentTimeMillis();
        tick = 0;
        // Minerals!
        for(Mineral mineral : data.minerals) {
            mineral.tick(chunk, time);
        }
        // Chunk unloading stuff
        if(time - lastActive > ConfigGlobal.bull.chunk.chunkUnloadDelay) {
            CDChunk.unload(chunk);
        }
    }

    /*
     * Event
     */

    @Override
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
        String instance = TagHelper.getString(tag, "chunk.moo", "");
        CDChunkData savedData = JsonHelper.getGson().fromJson(instance, CDChunkData.class);
        data = instance.length() > 0 ? savedData == null ? new CDChunkData() : savedData : new CDChunkData();
    }

    @Override
    public void onTagSave(NBTTagCompound tag) {
        tag.setString("chunk.moo", JsonHelper.toJson(data));
    }

    /*
     * Default
     */

    public Chunk getChunk() {
        return chunk;
    }

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
