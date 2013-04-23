package com.gmail.mooman219.module;

import org.bukkit.Chunk;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.entity.Entity;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;
import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class CDChunk implements CowData {
    public final Chunk chunk;

    public CDChunk(Chunk chunk) {
        this.chunk = chunk;
        onLoad(getHandle());
    }

    /*
     * Live
     */
    
    // Region
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
     * Tag
     */

    // Other
    public int test = 0;
    // Region
    public String parentUUID = "";
    
    @Override
    public void onLoad(CowTaggable handle) {
        test = TagHelper.getInt(handle.dataTag, "test", test);
        parentUUID = TagHelper.getString(handle.dataTag, "region.uuid", parentUUID);
    }
    
    @Override
    public void onSave(CowTaggable handle) {
        handle.clearStoreTag();
        handle.dataTag.setInt("test", test);
        handle.dataTag.setString("region.uuid", parentUUID);
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
        if(handle.dataLive == null) {
            handle.dataLive = new CDChunk(chunk);
        }
        // It should always be a CDChunk
        /** else if(!(handle.dataLive instanceof CDChunk)) {
            throw new IllegalArgumentException("Invalid data on chunk.");
        }**/
        return (CDChunk) handle.dataLive;
    }
}
