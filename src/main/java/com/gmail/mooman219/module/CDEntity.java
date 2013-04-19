package com.gmail.mooman219.module;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;
import com.gmail.mooman219.frame.TagHelper;

public class CDEntity implements CowData {
    public final Entity entity;

    private CDEntity(Entity entity) {
        this.entity = entity;
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

    public net.minecraft.server.Entity getHandle() {
        return ((CraftEntity)entity).getHandle();
    }

    public static CDEntity get(Entity entity) {
        net.minecraft.server.Entity handle = ((CraftEntity)entity).getHandle();
        if(entity instanceof Player) {
            throw new IllegalArgumentException("Players are not considered entities.");
        }
        if(handle.dataLive == null) {
            handle.dataLive = new CDEntity(entity);
        } else if(!(handle.dataLive instanceof CDEntity)) {
            throw new IllegalArgumentException("Invalid data on entity.");
        }
        return (CDEntity) handle.dataLive;
    }
}
