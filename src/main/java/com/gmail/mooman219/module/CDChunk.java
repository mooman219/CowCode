package com.gmail.mooman219.module;

import org.bukkit.Chunk;
import org.bukkit.craftbukkit.CraftChunk;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;
import com.gmail.mooman219.frame.TagHelper;

public class CDChunk implements CowData {
    public final Chunk chunk;

    public CDChunk(Chunk chunk) {
        this.chunk = chunk;
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

    public net.minecraft.server.Chunk getHandle() {
        return ((CraftChunk)chunk).getHandle();
    }

    public static CDChunk get(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.dataLive == null) {
            handle.dataLive = new CDChunk(chunk);
        } else if(!(handle.dataLive instanceof CDChunk)) {
            throw new IllegalArgumentException("Invalid data on chunk.");
        }
        return (CDChunk) handle.dataLive;
    }
}
